package com.osa.model;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Transfer implements Serializable {
    private static final long serialVersionUID = -2583423887168800287L;

    @XmlElement
    private Time departure;
    @XmlElement
    private Time arrival;
    @XmlElement
    private Station station;
}
