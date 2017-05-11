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
@XmlType(name = "station", propOrder = {
        "id",
        "code",
        "name",
        "address",
        "coordinates",
        "city",
        "country"
})
public class Station implements Serializable {
    private static final long serialVersionUID = -9088762482371387394L;

    private Long id;
    private String code;
    private String name;
    private String address;
    private Coordinates coordinates;
    private City city;
    private Country country;
}
