package com.servicenow.servicenowrf.rainfocus.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.servicenow.servicenowrf.rainfocus.configuration.Properties;
import com.servicenow.servicenowrf.rainfocus.dto.*;
import com.servicenow.servicenowrf.rainfocus.helpers.RFConstants;
import com.servicenow.servicenowrf.rainfocus.helpers.EventHelper;
import com.servicenow.servicenowrf.rainfocus.service.RFAuthTokenGenerator;
import com.servicenow.servicenowrf.rainfocus.service.RainFocusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class RainFocusServiceImpl implements RainFocusService {
    private static final String BAD_REQUEST = "400 Bad Request";
    private static final String AUTH_TOKEN = "&rfAuthToken=";

    private final Properties properties;
    private final RestTemplate restTemplate;
    private RFAuthTokenGenerator tokenGenerator;

    public RainFocusServiceImpl(Properties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setTokenGenerator(RFAuthTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public String updateAttendee(AttendeeDto attendeeDto) {
        if (attendeeDto != null) {
            log.info("Updating attendee");

            final String uri = properties.getRainfocusBaseUrl() + "/attendee/update?apiProfile=" + properties.getRainfocusApiProfileId();

            if (!StringUtils.hasLength(attendeeDto.getStateId())) {
                String validStateId = EventHelper.getStateAbbreviation(attendeeDto.getStateId());
                if (StringUtils.hasLength(validStateId))
                    attendeeDto.setStateId(validStateId);
                if (!"US".equalsIgnoreCase(attendeeDto.getCountryId()) && !"CA".equalsIgnoreCase(attendeeDto.getCountryId())) {
                    attendeeDto.setForeignstate(attendeeDto.getStateId());
                    attendeeDto.setStateId("");
                }
            }

            setOptIn(attendeeDto);

            try {
                final var response = restTemplate.postForEntity(uri, attendeeDto, JsonNode.class);
                log.info("Attendee update response from RF {}", response);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return response.getBody().get("attendeeId").asText();
                }
            } catch (HttpClientErrorException e) {
                log.error("Error updating attendee email {} {}", attendeeDto.getEmail(), e.getMessage());
            }

        }
        return null;
    }

    @Override
    @Cacheable(value = "acceptedSessionsForEvent", key = "#eventId")
    public Object getAllAcceptedSessions(String eventId) {
        log.info("Fetching all sessions for eventId -> {}", eventId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String sessionUri = parts[1] + "/feed/bwSessionData?rfApiProfileId=" + parts[0] + "&event=" + eventId;
            try {
                var response = restTemplate.getForEntity(sessionUri, JsonNode.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                    return response.getBody().get("items");
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception fetching sessions {}", e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String addSession(String eventId, String validId, String sessionTimeId) {
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String toggleSessionInterestUri = parts[1] + "/addSession?rfApiProfileId=" + parts[0] + "&waitlist=false&attendeeId=" + validId + "&event=" + eventId + "&sessionTimeId=" + sessionTimeId;
            try {
                ResponseEntity<String> exchange = restTemplate.exchange(toggleSessionInterestUri, HttpMethod.GET, null, String.class);
                if (exchange.getStatusCode().equals(HttpStatus.OK) && exchange.getBody() != null) {
                    return exchange.getBody();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception adding session {}", e.getMessage());
                if (Objects.requireNonNull(e.getMessage()).contains(BAD_REQUEST))
                    return e.getMessage().replace(BAD_REQUEST + ":", "");
            }
        }
        return null;
    }

    @Override
    public String removeSession(String eventId, String validId, String sessionTimeId) {
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String toggleSessionInterestUri = parts[1] + "/removeSession?rfApiProfileId=" + parts[0] + "&waitlist=false&attendeeId=" + validId + "&event=" + eventId + "&sessionTimeId=" + sessionTimeId;
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(toggleSessionInterestUri, String.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                    return response.getBody();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception removing session {}", e.getMessage());
                if (Objects.requireNonNull(e.getMessage()).contains(BAD_REQUEST))
                    return e.getMessage().replace(BAD_REQUEST + ":", "");
            }
        }
        return null;
    }

    @Override
    public Object getSessionAttended(String eventId, String attendeeId) {
        log.info("Fetching sessions attended for validId -> {} & eventId -> {}", attendeeId, eventId);
        if (attendeeId != null) {
            var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

            if (apiProfileAndUri != null) {
                String[] parts = apiProfileAndUri.split("~");
                String uri = parts[1] + "/session/attendance?rfApiProfileId=" + parts[0] + "&attended=true&attendeeId=" + attendeeId + "&event=" + eventId;
                try {
                    ResponseEntity<?> response = restTemplate.getForEntity(uri, String.class);
                    if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                        return response.getBody().toString();
                    }
                } catch (HttpClientErrorException e) {
                    log.error("Exception fetching attended sessions schedule {}", e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public List<SessionSchedule> getSessionScheduled(String eventId, String rfAuthToken, boolean attendeeId) {
        log.info("Fetching sessions schedule for validId -> {} & eventId -> {}", rfAuthToken, eventId);
        if (rfAuthToken != null) {
            if (attendeeId) {
                var token = tokenGenerator.generateAuthTokenByAttendeeId(eventId, rfAuthToken);
                if (token.isPresent()) {
                    rfAuthToken = token.get();
                }
            }

            var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

            if (apiProfileAndUri != null) {
                String[] parts = apiProfileAndUri.split("~");
                String scheduledUri = parts[1] + "/mySchedule?rfApiProfileId=" + parts[0] + AUTH_TOKEN + rfAuthToken + "&event=" + eventId;
                try {
                    var response = restTemplate.getForEntity(scheduledUri, SessionScheduleWrapper.class);
                    if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                        return response.getBody().getMySchedule();
                    }
                } catch (HttpClientErrorException e) {
                    log.error("Exception fetching session schedules {}", e.getMessage());
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Object fetchAllRelatedSessions(String eventId, String sessionId, String attendeeId) {
        log.info("Fetching all related sessions {} of attendeeId {} for eventId {}", eventId, attendeeId, sessionId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String sessionUri = parts[1] + "/similar?rfApiProfileId=" + parts[0] + "&event=" + eventId
                    + "&rfWidgetId=WmsBebiYIgaYMALphcmBHp1tNrSBMpUB&sessionId=" + sessionId + AUTH_TOKEN + tokenGenerator.generateAuthTokenByAttendeeId(eventId, attendeeId).get();
            try {
                var response = restTemplate.getForEntity(sessionUri, JsonNode.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return response.getBody().get("items");
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception fetching related sessions {}", e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String addSessionAttendance(String eventId, String validId, String sessionTimeId) {
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String toggleSessionInterestUri = parts[1] + "/addSessionAttendance?rfApiProfileId=" + parts[0] + "&attendeeId=" + validId + "&event=" + eventId + "&sessionTimeId=" + sessionTimeId;
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(toggleSessionInterestUri, String.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                    return response.getBody();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception adding session {}", e.getMessage());
                if (Objects.requireNonNull(e.getMessage()).contains(BAD_REQUEST))
                    return e.getMessage().replace(BAD_REQUEST + ":", "");
            }
        }
        return null;
    }

    @Override
    public List<SessionInterestDto> getSessionInterests(String eventId, String rfAuthToken, boolean attendeeId) {
        log.info("Fetching sessions interests for validId -> {} & eventId -> {}", rfAuthToken, eventId);
        if (rfAuthToken != null) {
            if (attendeeId) {
                var token = tokenGenerator.generateAuthTokenByAttendeeId(eventId, rfAuthToken);
                if (token.isPresent()) {
                    rfAuthToken = token.get();
                }
            }
            var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

            if (apiProfileAndUri != null) {
                String[] parts = apiProfileAndUri.split("~");
                String sessionInterestUri = parts[1] + "/myInterests?rfApiProfileId=" + parts[0] + AUTH_TOKEN + rfAuthToken + "&event=" + eventId;

                try {
                    var response = restTemplate.getForEntity(sessionInterestUri, SessionInterestWrapper.class);
                    if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                        return processSessionInterests(response.getBody().getSessionInterests());
                    }
                } catch (HttpClientErrorException e) {
                    log.error("Exception fetching session interests {}", e.getMessage());
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String toggleSessionsInterests(String eventId, String validId, String sessionId, boolean useAttendeeId) {
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String authToken = validId;

            if (useAttendeeId) {
                var token = tokenGenerator.generateAuthTokenByAttendeeId(eventId, validId);
                if (token.isPresent()) {
                    authToken = token.get();
                }
            }

            String toggleSessionInterestUri = parts[1] + "/toggleSessionInterest?rfApiProfileId=" + parts[0] + AUTH_TOKEN + authToken + "&event=" + eventId + "&sessionId=" + sessionId;
            try {
                var response = restTemplate.getForEntity(toggleSessionInterestUri, JsonNode.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return response.getBody().get("operation").asText();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception toggling session interests {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public AttendeeDto getAttendeeInfo(String validId, String eventId) {
        log.info("Fetching attendeeInfo for attendee -> {}, event -> {}", validId, eventId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String attendeeUri = parts[1] + "/attendeeInfo?rfApiProfileId=" + parts[0] + "&event=" + eventId + AUTH_TOKEN + validId;
            try {
                ResponseEntity<AttendeeInfoWrapper> response = restTemplate.getForEntity(attendeeUri, AttendeeInfoWrapper.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null && response.getBody().getResponseMessage().equals("Success")) {
                    return processAttendee(response.getBody().getAttendee());
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception fetching attendeeInfo {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String updateAttendeeInfo(String attendeeId, String eventId, String attributeCode, String score) {
        log.info("Updating score {} to RF for eventId {},clientId {}", score, eventId, attendeeId);
        var instanceUri = RFConstants.getInstanceUri(eventId);

        if (instanceUri != null) {
            String updateUri = instanceUri + "/attendee/update?apiProfile=" + RFConstants.getApiKey(eventId);

            try {
                ResponseEntity<String> exchange = restTemplate.exchange(updateUri, HttpMethod.POST, buildHeaderEntityByAttendeeId(attendeeId, eventId, score, attributeCode), String.class);
                if (exchange.getStatusCode().equals(HttpStatus.OK) && exchange.getBody() != null) {
                    return exchange.getBody();
                }
            } catch (Exception e) {
                log.error("Exception updating registrant score {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    @Cacheable(value = "allSpeakersForEvent", key = "#eventId")
    public List<SpeakerWrapper.Section> fetchSpeakers(String eventId) {
        log.info("Fetching speakers for eventID -> {}", eventId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String speakerUri = parts[1] + "/speakerData?rfApiProfileId=" + parts[0] + "&event=" + eventId + "&type=speaker";

            try {
                ResponseEntity<SpeakerWrapper> response = restTemplate.getForEntity(speakerUri, SpeakerWrapper.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                    return response.getBody().getSectionList();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception fetching speaker {} for eventID: {}", e.getMessage(), eventId);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Object createLead(String eventId, String attendeeId, String exhibitorId, String source, String notes, boolean hotLead) {
        log.info("Create lead for eventId {}, sponsorId {}, attendeeId {}", eventId, exhibitorId, attendeeId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            Optional<String> token = tokenGenerator.generateAuthTokenByAttendeeId(eventId, attendeeId);
            if (token.isPresent()) {
                String uri = parts[1] + "/createLead?rfApiProfileId=" + parts[0] + AUTH_TOKEN + token.get() + "&exhibitorId=" + exhibitorId + "&source=" + source + "&event=" + eventId + (!StringUtils.isEmpty(notes) ? "&notes=" + notes : "") + (hotLead ? "&hotLead=true" : "");
                try {
                    ResponseEntity<?> exchange = restTemplate.getForEntity(uri, String.class);
                    if (exchange.getStatusCode().equals(HttpStatus.OK) && exchange.getBody() != null) {
                        return exchange.getBody();
                    }
                } catch (HttpClientErrorException e) {
                    log.error("Error creating lead {}", e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    @Cacheable(value = "allSponsorsForEvent", key = "#eventId")
    public List<Exhibitor> fetchAllSponsors(String eventId) {
        log.info("Fetching all sponsors for eventId -> {}", eventId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String uri = parts[1] + "/entityDataDump/exhibitor?rfApiProfileId=" + parts[0] + "&event=" + eventId;
            try {
                var response = restTemplate.getForEntity(uri, ExhibitorWrapper.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null && response.getBody().getResponseMessage().equals("Success")) {
                    return response.getBody().getData();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception fetching all sponsors {}", e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Object playList(String eventId, String widgetId) {
        log.info("Fetching playList for eventId -> {}", eventId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String uri = parts[1] + "/collection?rfApiProfileId=" + parts[0] + "&rfWidgetId=" + widgetId + "&event=" + eventId;
            try {
                ResponseEntity<?> response = restTemplate.getForEntity(uri, String.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                    return response.getBody();
                }
            } catch (Exception e) {
                log.error("Exception fetching playList {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public AttendeeInfo getAttendeeInfoByAttendeeID(String validId, String eventId) {
        log.info("Fetching attendeeInfo for attendee -> {}, event -> {}", validId, eventId);
        var apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String attendeeUri = parts[1] + "/attendeeInfo?rfApiProfileId=" + parts[0] + "&event=" + eventId + "&attendeeId=" + validId;
            try {
                var response = restTemplate.getForEntity(attendeeUri, AttendeeInfoWrapper.class);
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null && response.getBody().getResponseMessage().equals("Success")) {
                    return response.getBody().getAttendee();
                }
            } catch (HttpClientErrorException e) {
                log.error("Exception fetching attendeeInfo {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public RainFocusDto getRainFocusDataForUser(String email, String eventCode) {
        String attendeeUri = "/attendeeInfo?event=" + eventCode + "&rfApiProfileId=" + properties.getRainfocusApiProfileId() + "&email=" + email;
        String url = properties.getRainfocusBaseUrl() + attendeeUri;

        try {
            var response = restTemplate.getForEntity(url, RainFocusDtoWrapper.class);
            log.info("RF response for email {} {}", email, response);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getResponseMessage().equals("Success")) {
                final var rainFocusDto = response.getBody().getAttendee();
                rainFocusDto.setCookieValue(rainFocusDto.isRegistered());
                return rainFocusDto;
            }
        } catch (HttpClientErrorException e) {
            log.error("Exception occurred in RF call {}", e.getMessage());
        }
        return null;
    }

    private void setOptIn(AttendeeDto attendeeDto) {
        Map<String, String> optInValues = new HashMap<>();
        optInValues.put("yes", "1587608247762001fDzP");
        optInValues.put("no", "1587608247762002fr4m");

        if (StringUtils.hasLength(attendeeDto.getOptInFlag())) {
            attendeeDto.setOptInFlag(optInValues.get(attendeeDto.getOptInFlag().toLowerCase()));
        }
    }

    private List<SessionInterestDto> processSessionInterests(List<SessionInterest> sessionInterests) {
        List<SessionInterestDto> sessionInterestDtos = new ArrayList<>();

        if (sessionInterests != null && !sessionInterests.isEmpty()) {
            sessionInterests.forEach(sessionInterest -> {
                SessionInterestDto sessionInterestDto = new SessionInterestDto();
                sessionInterestDto.setSessionId(sessionInterest.getSessionID());
                sessionInterestDto.setTitle(sessionInterest.getTitle());
                sessionInterestDto.setCode(sessionInterest.getCode());
                sessionInterestDto.setType(sessionInterest.getType());
                sessionInterestDto.setSessionTimeIds(sessionInterest.getTimes());
                sessionInterestDto.setAttributevalues(sessionInterest.getAttributevalues());
                sessionInterestDtos.add(sessionInterestDto);
            });
        }
        return sessionInterestDtos;
    }

    private AttendeeDto processAttendee(AttendeeInfo attendee) {
        AttendeeDto attendeeDto = new AttendeeDto();
        if (attendee != null) {
            attendeeDto.setAttendeeId(attendee.getAttendeeId());
            attendeeDto.setGuid(attendee.getGuid());
            attendeeDto.setFirstname(attendee.getFirstname());
            attendeeDto.setLastname(attendee.getLastname());
            attendeeDto.setClientId(attendee.getClientId());

            // Populate Packages - Knowledge 2021
            Set<String> packages = new HashSet<>();
            Optional<AttendeeInfo.Attribute> packagePurchased = attendee.getAttributes().stream().filter(attribute -> attribute.getName().equalsIgnoreCase("Package Purchased")).findFirst();
            packagePurchased.ifPresent(attribute -> attribute.getAttributeValues().forEach(attributeValue -> packages.add(attributeValue.getValue())));
            attendeeDto.setPackagePurchased(String.join(",", packages));
        }
        return attendeeDto;
    }

    private HttpEntity<Object> buildHeaderEntityByAttendeeId(String attendeeId, String eventId, String value, String customField) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("apiProfile", RFConstants.getApiKey(eventId));

        Map<String, String> body = new HashMap<>();
        body.put("event", eventId);
        body.put("attendeeId", attendeeId);
        body.put(customField, value);

        return new HttpEntity<>(body, headers);
    }
}
