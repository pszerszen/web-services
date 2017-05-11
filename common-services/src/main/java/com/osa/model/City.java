package com.osa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "city", propOrder = {
        "id",
        "name",
        "cityClass",
        "coordinates",
        "stations",
        "possibleDestinations",
        "country"
})
public class City implements Serializable {
    private static final long serialVersionUID = -8301402847341826707L;

    private Long id;
    private String name;
    private CityClass cityClass;
    private Coordinates coordinates;
    @XmlElement(name = "stations", type = Long.class)
    private List<Long> stations;
    @XmlElement(name = "possibleDestination", type = Long.class)
    private List<Long> possibleDestinations;
    private Country country;
}
