package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class SpeakerWrapper implements Serializable {
    private String responseCode;
    private String responseMessage;
    private String totalSearchItems;
    private List<Section> sectionList;
    private List<SpeakerDto> items;

    @Getter
    @Setter
    @ToString
    public static class Section implements Serializable {
        private List<SpeakerDto> items;
    }
}
