package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionTime {
    private String sessionTimeID;
    private String room;
    private String date;
    private String time;
    private String length;
}
