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
public class Network implements Serializable {
    private static final long serialVersionUID = -4555927183604575042L;

    @XmlElement
    private List<City> cities;
    @XmlElement
    private List<Station> stations;
}
