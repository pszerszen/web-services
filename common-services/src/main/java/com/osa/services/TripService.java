package com.osa.services;

import com.osa.generators.StationGenerator;
import com.osa.generators.TripItemGenerator;
import com.osa.model.Trip;
import com.osa.model.TripItem;
import com.osa.model.request.TripRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripService {

    private final StationGenerator stationGenerator;

    private final TripItemGenerator tripItemGenerator;

    public Trip searchTrip(TripRequest request, Integer expectedNumber) {
        int trips = Optional.ofNullable(expectedNumber)
                .orElseGet(TripService::generateNumberOfTrips);

        List<TripItem> items = IntStream.range(0, trips).boxed()
                .map(i -> tripItemGenerator.generateTripItem(
                        request.getDepartureDate()
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0),
                        request.getDepartureDate()
                                .withHour(23)
                                .withMinute(59)
                                .withSecond(59)))
                .collect(Collectors.toList());

        return Trip.builder()
                .from(stationGenerator.generateStation())
                .to(stationGenerator.generateStation())
                .items(items)
                .build();
    }

    private static int generateNumberOfTrips() {
        return RandomUtils.nextInt(0, 35);
    }
}
