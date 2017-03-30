package com.osa.model;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City implements Serializable {
    private static final long serialVersionUID = -8301402847341826707L;

    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement
    private CityClass cityClass;

    @XmlElement
    private Coordinates coordinates;

    @XmlElement
    private List<Long> stations;

    @XmlElement
    private List<Long> possibleDestinations;

    @XmlElement
    private Country country;
}
