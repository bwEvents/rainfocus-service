package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Event {
    private String eventName;
    private String eventCode;
    private String eventLongName;
    private String cityName;
    private String eventDescription;
    private String agendImageLink;
    private String blockBlusterDescription;
    private String fullConferencePrice;
    private String governmentPassPrice;
    private String learningLivePrice;
    private String videoLink;
    private String rsvpLink;
    private String contactUsEmail;
    private String knowledgeGoingTobeHeldFAQ;

}
