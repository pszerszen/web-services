package com.osa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @XmlElement(name = "station")
    private List<Long> stations;

    @XmlElement(name = "possibleDestination")
    private List<Long> possibleDestinations;

    @XmlElement
    private Country country;
}
