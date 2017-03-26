package com.osa.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class TripRequest implements Serializable {
    private static final long serialVersionUID = -8301402847341826707L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.mm.yyyy");

    SearchBy searchBy;
    String fromCityId;
    String toCityId;
    String fromStationId;
    String toStationId;
    int adult;
    int children;
    boolean bikes;
    Currency currency;
    String departureDate;

    public LocalDateTime getDepartureDate() {
        return LocalDateTime.parse(departureDate, DATE_TIME_FORMATTER);
    }
}
