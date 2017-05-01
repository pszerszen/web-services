package com.osa.generators;

import com.osa.model.Coordinates;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.RandomUtils.nextDouble;

@Component
public class CoordinatesGenerator {

    public Coordinates generateCoordinates() {
        return Coordinates.builder()
                .latitude(getRandomLatitude())
                .longitude(getRandomLongitude())
                .build();
    }

    private double getRandomLatitude() {
        return nextDouble(ExtremalEuropeCoordinates.SOUTH.getValue(), ExtremalEuropeCoordinates.NORTH.getValue());
    }

    private double getRandomLongitude() {
        return nextDouble() * (ExtremalEuropeCoordinates.EAST.getValue() - ExtremalEuropeCoordinates.WEST.getValue())
                - ExtremalEuropeCoordinates.WEST.getValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private enum ExtremalEuropeCoordinates {
        NORTH(71),
        SOUTH(36.5),
        EAST(53),
        WEST(-10);

        private final double value;
    }
}
