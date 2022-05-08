package com.servicenow.servicenowrf.rainfocus.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Properties {

    @Value("${grant-type}")
    private String grantType;
    @Value("${redirect-uri}")
    private String redirectUri;
    @Value("${okta.base-url}")
    private String oktaBaseUrl;
    @Value("${okta.client.id}")
    private String oktaClientId;
    @Value("${okta.client.secret}")
    private String oktaClientSecret;
    @Value("${okta.token-uri}")
    private String oktaTokenUri;
    @Value("${okta.user-uri}")
    private String oktaUserUri;
    @Value("${session.redirect-uri}")
    private String sessionRedirectUri;
    @Value("${rainfocus.api-profile-key}")
    private String rainfocusApiProfileId;
    @Value("${rainfocus.base-url}")
    private String rainfocusBaseUrl;
    // CDP Profile
    @Value("${cdp.clientId}")
    private String cdpClientId;
    @Value("${cdp.clientSecret}")
    private String cdpClientSecret;
    @Value("${cdp.apiKey}")
    private String cdpApiKey;
    @Value("${cdp.base-url}")
    private String cdpURI;

    //All Events
    @Value("${k22.active-events}")
    private List<String> events;


    @Value("${is.whitelisted.in.okta}")
    private boolean isWhiteListedInOkta;
}
