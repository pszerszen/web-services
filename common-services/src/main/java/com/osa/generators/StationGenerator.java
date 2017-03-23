package com.osa.generators;

import com.osa.model.Station;
import com.osa.model.util.RandomValuesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StationGenerator {

    private final CoordinatesGenerator coordinatesGenerator;

    private final CityGenerator cityGenerator;

    private final CountryGenerator countryGenerator;

    private final RandomValuesUtils randomValuesUtils;

    public Station generateStation() {
        return Station.builder()
                .id(RandomUtils.nextLong())
                .code(RandomStringUtils.randomNumeric(8))
                .name(randomValuesUtils.randomName())
                .city(cityGenerator.generateCity())
                .country(countryGenerator.generateCountry())
                .build();
    }

    public Station generateExtendedStation() {
        return Station.builder()
                .id(RandomUtils.nextLong())
                .code(RandomStringUtils.randomNumeric(8))
                .name(randomValuesUtils.randomName())
                .address(randomValuesUtils.randomAddress())
                .coordinates(coordinatesGenerator.generateCoordinates())
                .city(cityGenerator.generateCity())
                .country(countryGenerator.generateCountry())
                .build();
    }
}
