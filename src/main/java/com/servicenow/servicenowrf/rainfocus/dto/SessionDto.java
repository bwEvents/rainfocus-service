package com.servicenow.servicenowrf.rainfocus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SessionDto implements Serializable {
    @JsonProperty("session_id")
    private String id;

    @JsonProperty("code")
    private String code;

    private String title;

    @JsonProperty("abstract")
    private String sessionAbstract;

    @JsonProperty("session_status")
    private String status;

    private String published;

    @JsonProperty("start_date")
    private String startDateTime;

    @JsonProperty("end_date")
    private String endDateTime;

    private int length;
    private int capacity;

    @JsonProperty("seats_remaining")
    private int seatsRemaining;

    @JsonProperty("session_type")
    private String sessionType;

    @JsonProperty("session_category")
    private String sessionCategory;

    @JsonProperty("con: solutions library filter k22")
    private String conSolutionsLibraryFilterK22;

    @JsonProperty("con: session primary product")
    private String conSessionPrimaryProduct;

    @JsonProperty("reg: event registration type")
    private String regEventRegistrationType;

    @JsonProperty("de_session_image")
    private String deSessionImage;

    @JsonProperty("event_start_date")
    private String eventStartDate;

    @JsonProperty("event_end_date")
    private String eventEndDate;

    @JsonProperty("session_start_time")
    private String sessionStartTime;

    @JsonProperty("session_end_time")
    private String sessionEndTime;

    @JsonProperty("enrolled_count")
    private int enrolledCount;

    @JsonProperty("session_date")
    private String sessionDate;

    @JsonProperty("room")
    private String room;

    @JsonProperty("sessiontime_id")
    private String sessiontimeId;
    
    @JsonProperty("de: digital session format")
    private String deDigitalSessionFormat;

    @JsonProperty("resource title 1")
    private String resourceTitle1;

    @JsonProperty("de: resources 1:url")
    private String deResources1Url;

    @JsonProperty("resource title 2")
    private String resourceTitle2;

    @JsonProperty("de: resources 2:url")
    private String deResources2Url;

    @JsonProperty("resource title 3")
    private String resourceTitle3;

    @JsonProperty("de: resources 3:url")
    private String deResources3Url;

    @JsonProperty("resource title 4")
    private String resourceTitle4;

    @JsonProperty("de: resources 4:url")
    private String deResources4Url;

    @JsonProperty("resource title 5")
    private String resourceTitle5;

    @JsonProperty("de: resources 5:url")
    private String deResources5Url;

    // For internal purpose
    private String eventId;
}
