package com.servicenow.servicenowrf.rainfocus.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ExhibitorResource implements Serializable {
    private String fileName;
    private String fileType;
    private String fileURL;
    private String published;
}
