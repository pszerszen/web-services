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
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 2830304317758738048L;

    @XmlElement
    private Double latitude;
    @XmlElement
    private Double longitude;
}
