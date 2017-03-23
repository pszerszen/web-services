package com.osa.generators;

import com.osa.model.City;
import com.osa.model.CityClass;
import com.osa.model.util.RandomValuesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CityGenerator {

    private final RandomValuesUtils randomValuesUtils;

    private final CoordinatesGenerator coordinatesGenerator;

    private final CountryGenerator countryGenerator;

    public City generateCity() {
        return City.builder()
                .id(RandomUtils.nextLong())
                .name(randomValuesUtils.randomName())
                .cityClass(CityClass.getRandomCityClass())
                .country(countryGenerator.generateCountry())
                .build();
    }

    public City generateExtendedCity() {
        return City.builder()
                .id(RandomUtils.nextLong())
                .name(randomValuesUtils.randomName())
                .cityClass(CityClass.getRandomCityClass())
                .coordinates(coordinatesGenerator.generateCoordinates())
                .stations(randomValuesUtils.randomStationsIndexes())
                .possibleDestinations(randomValuesUtils.randomDestinationCities())
                .country(countryGenerator.generateCountry())
                .build();
    }

}
