package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SessionInterestWrapper {
    private String responseCode;
    private List<SessionInterest> sessionInterests;
}
