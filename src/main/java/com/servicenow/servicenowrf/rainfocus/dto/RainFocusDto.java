package com.servicenow.servicenowrf.rainfocus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.servicenow.servicenowrf.rainfocus.helpers.RFConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RainFocusDto implements Serializable {
    private String attendeeId;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("companyname")
    private String companyName;
    private String email;
    @JsonProperty("jobtitle")
    private String jobTitle;
    @JsonProperty("isRegistered")
    private boolean isRegistered;
    private boolean cookieValue;
    private String attendeeType;
    private String marketingOptin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<AttendeeInfo.Attribute> attributes;

    public String getAttendeeType() {
        if (StringUtils.hasLength(attendeeType))
            return attendeeType;

        if (attributes == null || attributes.isEmpty())
            return "";

        // Find Attendee type
        for (int i = 0; i < attributes.size(); i++) {
            AttendeeInfo.Attribute attribute = attributes.get(i);
            if (RFConstants.ATTRIBUTE_CODE_ATTENDEE_TYPE.equalsIgnoreCase(attribute.getCode())) {
                List<AttendeeInfo.Attribute.AttributeValue> values = attribute.getAttributeValues();
                if (values == null || attributes.isEmpty())
                    return "";
                return values.get(0).getValue();
            }
        }
        return attendeeType;
    }

    public String getMarketingOptin() {
        if (StringUtils.hasLength(marketingOptin))
            return marketingOptin;

        if (attributes == null || attributes.isEmpty())
            return "No";

        // Find Marketing Optin
        for (int i = 0; i < attributes.size(); i++) {
            AttendeeInfo.Attribute attribute = attributes.get(i);
            if (RFConstants.ATTRIBUTE_CODE_MARKETING_OPT_IN.equalsIgnoreCase(attribute.getCode())) {
                List<AttendeeInfo.Attribute.AttributeValue> values = attribute.getAttributeValues();
                if (values == null || attributes.isEmpty())
                    return "No";
                return values.get(0).getValue();
            }
        }
        return "No";
    }
}
