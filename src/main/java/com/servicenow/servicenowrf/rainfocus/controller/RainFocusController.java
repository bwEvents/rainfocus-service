package com.servicenow.servicenowrf.rainfocus.controller;

import com.servicenow.servicenowrf.rainfocus.dto.*;
import com.servicenow.servicenowrf.rainfocus.service.RainFocusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class RainFocusController {
    private final RainFocusService rainFocusService;

    public RainFocusController(RainFocusService rainFocusService) {
        this.rainFocusService = rainFocusService;
    }

    @PostMapping("/update-attendee")
    public ResponseEntity<String> updateAttendee(@RequestBody AttendeeDto attendeeDto) {
        final var attendeeId = rainFocusService.updateAttendee(attendeeDto);
        if (attendeeId != null) {
            return ResponseEntity.ok(attendeeId);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/sessionsAllAccepted/{eventId}")
    public ResponseEntity<Object> getAllAcceptedSessions(@PathVariable String eventId) {
        if (StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.getAllAcceptedSessions(eventId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/addSession")
    public ResponseEntity<String> addSessionTimeID(@RequestParam("eventId") String eventId, @RequestParam("validId") String validId, @RequestParam("sessionTimeId") String sessionTimeId) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(validId) && StringUtils.hasLength(sessionTimeId)) {
            return new ResponseEntity<>(rainFocusService.addSession(eventId, validId, sessionTimeId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/removeSession")
    public ResponseEntity<String> removeSessionTimeID(@RequestParam("eventId") String eventId, @RequestParam("validId") String validId, @RequestParam("sessionTimeId") String sessionTimeId) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(validId) && StringUtils.hasLength(sessionTimeId)) {
            return new ResponseEntity<>(rainFocusService.removeSession(eventId, validId, sessionTimeId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/attended")
    public ResponseEntity<Object> getAllSessionsAttended(@RequestParam("eventId") String eventId, @RequestParam("attendeeId") String attendeeId) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(attendeeId)) {
            return new ResponseEntity<>(rainFocusService.getSessionAttended(eventId, attendeeId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/schedule")
    public ResponseEntity<List<SessionSchedule>> getAllSessionsSchedule(@RequestParam("eventId") String eventId, @RequestParam("validId") String validId, @RequestParam(value = "useAttendeeID", required = false) boolean useAttendeeID) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(validId)) {
            return new ResponseEntity<>(rainFocusService.getSessionScheduled(eventId, validId, useAttendeeID), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/sessionsRelated/{eventId}")
    public ResponseEntity<Object> getAllRelatedSessions(@PathVariable String eventId, @RequestParam(value = "sessionId") String sessionId, @RequestParam(value = "attendeeId") String attendeeId) {
        if (StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.fetchAllRelatedSessions(eventId, sessionId, attendeeId).toString(), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/addSessionAttendance")
    public ResponseEntity<String> addSessionAttendance(@RequestParam("eventId") String eventId, @RequestParam("validId") String validId, @RequestParam("sessionTimeId") String sessionTimeId) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(validId) && StringUtils.hasLength(sessionTimeId)) {
            return new ResponseEntity<>(rainFocusService.addSessionAttendance(eventId, validId, sessionTimeId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/interests")
    public ResponseEntity<List<SessionInterestDto>> getAllSessionsInterests(@RequestParam("eventId") String eventId, @RequestParam("validId") String validId, @RequestParam(value = "useAttendeeID", required = false) boolean useAttendeeID) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(validId)) {
            return new ResponseEntity<>(rainFocusService.getSessionInterests(eventId, validId, useAttendeeID), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sessions/interests/toggle")
    public ResponseEntity<String> toggleSessionsInterests(@RequestParam("eventId") String eventId, @RequestParam("validId") String validId, @RequestParam("sessionId") String sessionId, @RequestParam(value = "useAttendeeId", required = false) boolean useAttendeeID) {
        if (StringUtils.hasLength(eventId) && StringUtils.hasLength(validId) && StringUtils.hasLength(sessionId)) {
            return new ResponseEntity<>(rainFocusService.toggleSessionsInterests(eventId, validId, sessionId, useAttendeeID), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/attendees/info")
    public ResponseEntity<?> getAttendeeInfo(@RequestParam("validId") String validId, @RequestParam("eventId") String eventId, @RequestParam(value = "useAttendeeId", required = false) boolean useAttendeeId) {
        if (StringUtils.hasLength(validId) && StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(useAttendeeId ? rainFocusService.getAttendeeInfoByAttendeeID(validId, eventId) : rainFocusService.getAttendeeInfo(validId, eventId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/attendees/score")
    public ResponseEntity<String> updateAttendeeGameScore(@RequestParam("registrantId") String registrantId, @RequestParam("eventId") String eventId,
                                                          @RequestParam("gameCode") String gameCode, @RequestParam("score") String score) {
        if (StringUtils.hasLength(registrantId) && StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.updateAttendeeInfo(registrantId, eventId, gameCode, score), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/allSpeakers")
    public ResponseEntity<List<SpeakerWrapper.Section>> speakersDump(@RequestParam("eventId") String eventId) {
        if (StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.fetchSpeakers(eventId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/allSponsors")
    public ResponseEntity<List<Exhibitor>> sponsorsDump(@RequestParam("eventId") String eventId, @RequestParam(value ="fileType", required = false) String fileType) {
        if (StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.fetchAllSponsors(eventId, fileType), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/widgets/playlist")
    public ResponseEntity<?> playList(@RequestParam("eventId") String eventId, @RequestParam("widgetId") String widgetId) {
        if (StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.playList(eventId, widgetId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/exhibitor/createLead")
    public ResponseEntity<?> createLead(@RequestParam("eventId") String eventId, @RequestParam("attendeeId") String attendeeId, @RequestParam("exhibitorId") String exhibitorId,
                                        @RequestParam("source") String source, @RequestParam("notes") String notes, @RequestParam("hotLead") boolean hotLead) {
        if (StringUtils.hasLength(eventId)) {
            return new ResponseEntity<>(rainFocusService.createLead(eventId, attendeeId, exhibitorId, source, notes, hotLead), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.BAD_REQUEST);
    }
}
