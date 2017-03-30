package com.osa.model;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Country implements Serializable {
    private static final long serialVersionUID = 162088827893700838L;

    @XmlElement
    private String name;
    @XmlElement
    private String code;
}
