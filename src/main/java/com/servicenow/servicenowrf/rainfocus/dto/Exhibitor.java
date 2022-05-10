package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Exhibitor implements Serializable {
    private String exhibitorId;
    private String name;
    private String code;
    private String description;
    private String published;
    private String url;
    private String demo;
    private List<ExhibitorResource> files;
    private List<AttributeValue> attributeValues;
}
