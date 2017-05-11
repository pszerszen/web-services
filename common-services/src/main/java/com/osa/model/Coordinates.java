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
@XmlType(name = "coordinates", propOrder = {
        "latitude",
        "longitude"
})
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 2830304317758738048L;

    private Double latitude;
    private Double longitude;
}
