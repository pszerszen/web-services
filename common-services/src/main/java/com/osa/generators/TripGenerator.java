package com.osa.generators;

import com.osa.model.Trip;
import com.osa.model.TripItem;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripGenerator {

    private final StationGenerator stationGenerator;

    private final TripItemGenerator tripItemGenerator;

    public Trip generateTrip() {
        int tripOffers = RandomUtils.nextInt(1, 31);

        LocalDateTime from = LocalDateTime.now().plusWeeks(1);
        LocalDateTime to = from.plusHours(12);

        List<TripItem> tripItemList = IntStream.range(0, tripOffers).boxed()
                .map(i -> tripItemGenerator.generateTripItem(from, to))
                .sorted()
                .collect(Collectors.toList());

        return Trip.builder()
                .from(stationGenerator.generateStation())
                .to(stationGenerator.generateStation())
                .items(tripItemList)
                .build();
    }
}
