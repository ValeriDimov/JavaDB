package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatPlayerDTO {

    @XmlElement
    private long id;

    public ImportStatPlayerDTO() {
    }

    public long getId() {
        return id;
    }
}
