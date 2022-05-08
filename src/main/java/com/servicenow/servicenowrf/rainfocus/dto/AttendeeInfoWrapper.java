package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AttendeeInfoWrapper {
    private String responseMessage;
    private AttendeeInfo attendee;
    private AttendeeInfo data;
    private int numItems;
    
    private List<AttendeeInfo> results;
    
    private List<AttendeeInfo> items;
}
