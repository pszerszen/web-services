package com.osa.generators;

import com.osa.model.City;
import com.osa.model.Network;
import com.osa.model.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NetworkGenerator {

    private final CityGenerator cityGenerator;

    private final StationGenerator stationGenerator;

    public Network generateNetwork(int cities, int stations) {
        List<City> cityList = IntStream.range(0, cities).boxed()
                .map(i -> cityGenerator.generateExtendedCity())
                .collect(Collectors.toList());

        List<Station> stationList = IntStream.range(0, stations).boxed()
                .map(i -> stationGenerator.generateExtendedStation())
                .collect(Collectors.toList());

        return Network.builder()
                .cities(cityList)
                .stations(stationList)
                .build();
    }
}
