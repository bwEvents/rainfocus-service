package com.servicenow.servicenowrf.rainfocus.service;

import com.servicenow.servicenowrf.rainfocus.dto.*;

import java.util.List;

public interface RainFocusService {
    RainFocusDto getRainFocusDataForUser(String email, String eventCode);

    List<SessionDto> getAllSessionsForAnEvent(String eventId);

    String updateAttendee(AttendeeDto attendeeDto);

    List<SessionDto> getAllAcceptedSessions(String eventId);

    String addSession(String eventId, String validId, String sessionTimeId);

    String removeSession(String eventId, String validId, String sessionTimeId);

    Object getSessionAttended(String eventId, String attendeeId);

    List<SessionSchedule> getSessionScheduled(String eventId, String rfAuthToken, boolean attendeeId);

    Object fetchAllRelatedSessions(String eventId, String sessionId, String attendeeId);

    String addSessionAttendance(String eventId, String validId, String sessionTimeId);

    List<SessionInterestDto> getSessionInterests(String eventId, String rfAuthToken, boolean attendeeId);

    String toggleSessionsInterests(String eventId, String validId, String sessionId, boolean useAttendeeId);

    AttendeeInfo getAttendeeInfoByAttendeeID(String validId, String eventId);

    AttendeeDto getAttendeeInfo(String validId, String eventId);

    String updateAttendeeInfo(String attendeeId, String eventId, String attributeCode, String score);

    List<SpeakerWrapper.Section> fetchSpeakers(String eventId);

    List<Exhibitor> fetchAllSponsors(String eventId);

    Object playList(String eventId, String widgetId);

    Object createLead(String eventId, String attendeeId, String exhibitorId, String source, String notes, boolean hotLead);
}
