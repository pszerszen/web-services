package com.osa.generators;

import com.osa.model.Coordinates;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesGenerator {

    public Coordinates generateCoordinates() {
        return Coordinates.builder()
                .latitude(getRandomLatitude())
                .longtitude(getRandomLongtitude())
                .build();
    }

    private double getRandomLatitude() {
        return RandomUtils.nextDouble(ExtremalEuropeCoordinates.SOUTH.getValue(), ExtremalEuropeCoordinates.NORTH.getValue());
    }

    private double getRandomLongtitude() {
        return RandomUtils.nextDouble(ExtremalEuropeCoordinates.WEST.getValue(), ExtremalEuropeCoordinates.EAST.getValue());
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
