package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class AttributeValue implements Serializable {
    private String value;
    private String attribute_id;
    private String attribute;
    private String rf_attribute_id;
    private String name;
    private String type;
    private String code;

    private List<Object> values;

}
