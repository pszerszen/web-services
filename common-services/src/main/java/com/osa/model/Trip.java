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
        "from",
        "to",
        "items"
})
@XmlRootElement(name = "trip")
public class Trip implements Serializable {
    private static final long serialVersionUID = 7543478626620070263L;

    private Station from;
    private Station to;
    @XmlElement(name = "item")
    private List<TripItem> items;

    public List<TripItem> getItem() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return this.items;
    }
}
