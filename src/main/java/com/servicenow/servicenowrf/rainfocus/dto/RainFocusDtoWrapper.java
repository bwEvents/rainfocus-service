package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RainFocusDtoWrapper {
    private String responseCode;
    private String responseMessage;
    private RainFocusDto attendee;
}
