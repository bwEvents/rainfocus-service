package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SessionSchedule {
    private String sessionID;
    private String sessiontime_id;
    private String title;
    private String code;
    private String date;
    private long length;
    private String startTime;
    private String endTime;
    private String sessionType;
    private String type;
    private String room_name;
    private String sessionTimeID;

    private List<AttributeValue> attributevalues;
    private List<SessionTime> times;
}
