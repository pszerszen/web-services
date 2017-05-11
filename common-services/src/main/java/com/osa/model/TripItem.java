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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tripItem", propOrder = {
        "id",
        "departure",
        "arrival",
        "price",
        "transfers",
        "status",
        "links"
})
public class TripItem implements Serializable, Comparable<TripItem> {
    private static final long serialVersionUID = -3693649260148803427L;

    private Long id;
    private Time departure;
    private Time arrival;
    private double price;
    @XmlElement(name = "transfer")
    private List<Transfer> transfers;
    private Status status;
    @XmlElement(name = "link")
    private List<Link> links;

    public List<Transfer> getTransfers() {
        if (transfers == null) {
            transfers = new ArrayList<>();
        }
        return this.transfers;
    }

    public List<Link> getLinks() {
        if (links == null) {
            links = new ArrayList<>();
        }
        return this.links;
    }

    @Override
    public int compareTo(final TripItem o) {
        return Objects.compare(departure, o.getDeparture(), Time::compareTo);
    }
}
