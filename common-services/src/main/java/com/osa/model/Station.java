package com.osa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Station implements Serializable {
    private static final long serialVersionUID = -9088762482371387394L;

    @XmlElement
    private Long id;
    @XmlElement
    private String code;
    @XmlElement
    private String name;
    @XmlElement
    private String address;
    @XmlElement
    private Coordinates coordinates;
    @XmlElement
    private City city;
    @XmlElement
    private Country country;
}
