package com.servicenow.servicenowrf.rainfocus.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class SpeakerDto implements Serializable {
    @JsonProperty("speakerId")
    @JsonAlias("speakerid")
    private String speakerId;
    
    @JsonProperty("firstName")
    @JsonAlias("firstname")
    private String firstName;
    
    @JsonProperty("lastName")
    @JsonAlias("lastname")
    private String lastName;
    
    private String fullName;
    private String bio;
    
    @JsonProperty("companyName")
    @JsonAlias("companyname")
    private String companyName;
    
    @JsonProperty("jobTitle")
    @JsonAlias("jobtitle")
    private String jobTitle;
    private List<SpeakerSession> session;
    private List<AttributeValue> attributevalues;
    private String photoURL;
    
    @JsonProperty("eventcode")
    private String eventcode;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("sessiontimeid")
    private String sessiontimeid;
    @JsonProperty("sessionstartdate")
    private String sessionstartdate;
    @JsonProperty("sessionstarttime")
    private String sessionstarttime;
    @JsonProperty("sessionendtime")
    private String sessionendtime;
    @JsonProperty("featuredspeaker")
    private String featuredspeaker;
    @JsonProperty("marketingimageurl")
    private String marketingimageurl;

    @Getter
    @Setter
    @ToString
    public static class SpeakerSession implements Serializable{
        private String sessionID;
        private String abbreviation;
        private String title;
        private String published;
        private String speakerRole;
        private String status;
    }
}
