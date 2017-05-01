package com.osa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Time implements Serializable, Comparable<Time> {
    private static final long serialVersionUID = 4295105571293533233L;

    @XmlElement
    private long timestamp;
    @XmlElement
    private String timezone;

    @Override
    public int compareTo(final Time o) {
        return Objects.compare(timestamp, o.getTimestamp(), Long::compareTo);
    }
}
