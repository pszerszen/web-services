package com.osa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cities",
        "stations"
})
@XmlRootElement(name = "network")
public class Network implements Serializable {
    private static final long serialVersionUID = -4555927183604575042L;

    @XmlElement(name = "cities")
    private List<City> cities;
    @XmlElement(name = "stations")
    private List<Station> stations;

    public List<City> getCities() {
        if (cities == null) {
            cities = new ArrayList<>();
        }
        return this.cities;
    }

    public List<Station> getStations() {
        if (stations == null) {
            stations = new ArrayList<>();
        }
        return this.stations;
    }
}
