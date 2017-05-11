package com.osa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transfer", propOrder = {
        "departure",
        "arrival",
        "station"
})
public class Transfer implements Serializable {
    private static final long serialVersionUID = -2583423887168800287L;

    private Time departure;
    private Time arrival;
    private Station station;
}
