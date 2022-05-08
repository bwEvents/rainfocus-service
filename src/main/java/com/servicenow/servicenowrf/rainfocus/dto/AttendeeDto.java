package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeDto {
    private String event;
    private String email;
    private String jobtitle;
    private String companyname;
    private String stateId;
    private String countryId;
    private String phone;
    private String foreignstate;
    private String optInFlag;
    private String cid;
    private String cmaid;
    private String cmcid;
    private String cmpid;
    private String cmrid;
    private String dclid;
    private String gclid;
    private String referencesource;
    private String campid;

    private String attendeeId;
    private String guid;
    private String firstname;
    private String lastname;
    private String packagePurchased;
    private String clientId;
}
