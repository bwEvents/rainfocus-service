package com.servicenow.servicenowrf.rainfocus.helpers;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class RFConstants {
    private static final String NOT_FOUND = "The event code {} is not configured. Please add the configuration into RFConstants first.";
    public static final String ATTRIBUTE_CODE_ATTENDEE_TYPE = "attendeeType";
    public static final String ATTRIBUTE_CODE_MARKETING_OPT_IN = "optInFlag";

    /**
     * @param eventCode eventCode
     * @return apiProfile, instance URI & eventTime Zone (Optional) separated by ~
     */
    public static String apiProfileAndUri(String eventCode) {
        Map<String, String> eventAndProfileUri = new HashMap<>();
        eventAndProfileUri.put("knowledge2022", "1hGBArn60dAcDabjKLw7jC7s7uRbwNKY~https://events.rainfocus.com/api/servicenow/v2");
        eventAndProfileUri.put("k22lasvegas", "1hGBArn60dAcDabjKLw7jC7s7uRbwNKY~https://events.rainfocus.com/api/servicenow/v2");
        eventAndProfileUri.put("k22newyork", "1hGBArn60dAcDabjKLw7jC7s7uRbwNKY~https://events.rainfocus.com/api/servicenow/v2");
        eventAndProfileUri.put("k22sydney", "1hGBArn60dAcDabjKLw7jC7s7uRbwNKY~https://events.rainfocus.com/api/servicenow/v2");
        eventAndProfileUri.put("k22netherlands", "1hGBArn60dAcDabjKLw7jC7s7uRbwNKY~https://events.rainfocus.com/api/servicenow/v2");
        return eventAndProfileUri.get(eventCode);
    }

    /**
     * @param eventCode event code
     * @return RFApiProfileKey
     */
    public static String getApiKey(String eventCode) {
        String profileAndUri = apiProfileAndUri(eventCode);
        if (profileAndUri != null) {
            String[] parts = profileAndUri.split("~");
            return parts[0];
        }
        log.error(NOT_FOUND, eventCode);
        return null;
    }

    /**
     * @param eventCode event code
     * @return base URL of event
     */
    public static String getInstanceUri(String eventCode) {
        String profileAndUri = apiProfileAndUri(eventCode);
        if (profileAndUri != null) {
            String[] parts = profileAndUri.split("~");
            return parts[1];
        }
        log.error(NOT_FOUND, eventCode);
        return null;
    }
}
