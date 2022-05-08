package com.servicenow.servicenowrf.rainfocus.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Properties {
    @Value("${rainfocus.api-profile-key}")
    private String rainfocusApiProfileId;
    @Value("${rainfocus.base-url}")
    private String rainfocusBaseUrl;

    //All Events
    @Value("${k22.active-events}")
    private List<String> events;
}
