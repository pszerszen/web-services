package com.osa.model.request;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TripRequest implements Serializable {
    private static final long serialVersionUID = -8301402847341826707L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.mm.yyyy");

    @XmlElement
    private SearchBy searchBy;

    @XmlElement
    private String fromCityId;

    @XmlElement
    private String toCityId;

    @XmlElement
    private String fromStationId;

    @XmlElement
    private String toStationId;

    @XmlElement
    private int adult;

    @XmlElement
    private int children;

    @XmlElement
    private boolean bikes;

    @XmlElement
    private Currency currency;

    @XmlElement
    private String departureDate;

    public LocalDateTime getDepartureDate() {
        return LocalDateTime.parse(departureDate, DATE_TIME_FORMATTER);
    }
}
