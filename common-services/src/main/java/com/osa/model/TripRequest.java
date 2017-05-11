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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "searchBy",
        "fromCityId",
        "toCityId",
        "fromStationId",
        "toStationId",
        "adult",
        "children",
        "bikes",
        "currency",
        "departureDate"
})
@XmlRootElement(name = "tripRequest")
public class TripRequest implements Serializable {
    private static final long serialVersionUID = -8301402847341826707L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private SearchBy searchBy;
    private String fromCityId;
    private String toCityId;
    private String fromStationId;
    private String toStationId;
    private int adult;
    private int children;
    private boolean bikes;
    private Currency currency;
    @XmlElement(required = true)
    private String departureDate;

    public LocalDateTime getDepartureDateAsLocalDateTime() {
        return LocalDate.parse(getDepartureDate(), DATE_TIME_FORMATTER).atStartOfDay();
    }
}
