package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SessionResponseWrapper {
    private int numItems;
    private List<SessionDto> items;
    private String responseMessage;
    private String responseCode;
}
