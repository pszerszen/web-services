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
public class Trip implements Serializable {
    private static final long serialVersionUID = 7543478626620070263L;

    @XmlElement
    private Station from;
    @XmlElement
    private Station to;
    @XmlElement
    private List<TripItem> items;
}
