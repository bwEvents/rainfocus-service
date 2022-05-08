package com.servicenow.servicenowrf.rainfocus.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.servicenow.servicenowrf.rainfocus.helpers.RFConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class RFAuthTokenGenerator {
    private RestTemplate restTemplate;

    public Optional<String> generateAuthTokenByClientId(String eventId, String clientId) {
        log.info("Generating auth token for clientId -> {} and eventId -> {}", clientId, eventId);
        String apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String tokenEndPoint = parts[1] + "/jwt?rfApiProfileId=" + parts[0] + "&event=" + eventId + "&clientId=" + clientId;
            return processResponse(tokenEndPoint);
        }
        return Optional.empty();
    }

    public Optional<String> generateAuthTokenByAttendeeId(String eventId, String attendeeID) {
        log.info("Generating JWT token for attendeeID -> {} and eventId -> {}", attendeeID, eventId);
        String apiProfileAndUri = RFConstants.apiProfileAndUri(eventId);

        if (apiProfileAndUri != null) {
            String[] parts = apiProfileAndUri.split("~");
            String tokenEndPoint = parts[1] + "/jwt?rfApiProfileId=" + parts[0] + "&event=" + eventId + "&userKey=" + attendeeID;
            return processResponse(tokenEndPoint);
        }
        return Optional.empty();
    }

    private Optional<String> processResponse(String tokenEndPoint) {
        try {
            var response = restTemplate.getForEntity(tokenEndPoint, JsonNode.class);
            if (response.getStatusCode().is2xxSuccessful() && null != response.getBody()) {
                String token = response.getBody().get("authToken").asText();
                log.info("Auth token generated successfully!");
                return Optional.of(token);
            }
        } catch (RestClientException e) {
            log.error("Exception in getting JWT token {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
