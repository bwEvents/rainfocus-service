package com.servicenow.servicenowrf.rainfocus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class AttendeeInfo implements Serializable {
    @JsonProperty("attendee id")
    private String attendeeIdWithSpace;
    private String attendeeId;
    private String guid;
    @JsonProperty("first name")
    private String firstname;
    @JsonProperty("last name")
    private String lastname;
    private List<Attribute> attributes;
    private String clientId;
    @JsonProperty("attendee type")
    private String attendeeType;
    @JsonProperty("allocated points")
    private long allocatedPoints;
    @JsonProperty("# of points")
    private long points;
    private String country;
    @JsonProperty("customer region")
    private String region;
    @JsonProperty("company name")
    private String companyName;
    @JsonProperty("job title")
    private String jobTitle;
    @JsonProperty("is guest?")
    private String isGuest;
    @JsonProperty("parent id")
    private String parentId;
    @JsonProperty("age")
    private String age;
    @JsonProperty("travel air check in")
    private String airCheckin;
    @JsonProperty("travel air check out")
    private String airCheckOut;
    @JsonProperty("winners club registration task status book flight complete")
    private String flightComplete;
    @JsonProperty("winners club registration task status rsvp complete")
    private String rsvpComplete;
    @JsonProperty("winners club registration task status book hotel complete")
    private String hotelComplete;
    @JsonProperty("winners club registration task status build trip complete")
    private String tripComplete;
    @JsonProperty("winners club activities hotel")
    private String activitiesHotel;
    private String language;
    private String registrationDate;
    @JsonProperty("isRegistered")
    private boolean isRegistered;

    @Getter
    @Setter
    @ToString
    public static class Attribute implements Serializable {
        private String attributeId;
        private String name;
        private List<AttributeValue> attributeValues;
        private String value;
        private String code;

        @Getter
        @Setter
        @ToString
        public static class AttributeValue implements Serializable {
            private String value;
        }
    }
}
