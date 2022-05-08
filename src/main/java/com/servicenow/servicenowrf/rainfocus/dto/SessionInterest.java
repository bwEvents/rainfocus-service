package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SessionInterest {
    private String sessionID;
    private String title;
    private String type;
    private String code;
    private List<SessionTime> times;
    private List<AttributeValue> attributevalues;

    @Getter
    @Setter
    @ToString
    public static class SessionTime {
        private String sessionTimeID;
        private long length;
        private String date;
        private String startTime;
        private String endTime;
        private String room;
    }
}
