package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SessionInterestDto {
    private String sessionId;
    private String title;
    private String code;
    private String type;
    private List<SessionInterest.SessionTime> sessionTimeIds;
    private List<AttributeValue> attributevalues;
}
