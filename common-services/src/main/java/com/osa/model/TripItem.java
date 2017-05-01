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
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class TripItem implements Serializable, Comparable<TripItem> {
    private static final long serialVersionUID = -3693649260148803427L;

    @XmlElement
    private Long id;
    @XmlElement
    private Time departure;
    @XmlElement
    private Time arrival;
    @XmlElement
    private double price;
    @XmlElement(name = "transfer")
    private List<Transfer> transfers;
    @XmlElement
    private Status status;
    @XmlElement(name = "link")
    private List<Link> links;

    @Override
    public int compareTo(final TripItem o) {
        return Objects.compare(departure, o.getDeparture(), Time::compareTo);
    }
}
