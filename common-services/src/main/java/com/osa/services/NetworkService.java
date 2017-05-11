package com.osa.services;

import com.osa.generators.NetworkGenerator;
import com.osa.generators.StationGenerator;
import com.osa.model.Network;
import com.osa.model.Station;
import com.osa.model.StationList;
import com.osa.properties.NetworkProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NetworkService {

    private final NetworkGenerator networkGenerator;
    private final StationGenerator stationGenerator;

    private final NetworkProperties properties;

    public Network getNetwork() {
        return networkGenerator.generateNetwork(properties.getCities().getRandom(), properties.getStations().getRandom());
    }

    public StationList getOriginStations() {
        return getStations(properties.getStations().getRandom());
    }

    public StationList getDestinationStations() {
        return getStations(properties.getDestinationsStations().getRandom());
    }

    private StationList getStations(int rangeClose) {
        List<Station> stations = IntStream.range(0, rangeClose).boxed()
                .map(i -> stationGenerator.generateExtendedStation())
                .collect(Collectors.toList());
        return StationList.builder().stations(stations).build();
    }
}
