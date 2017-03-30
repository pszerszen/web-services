package com.osa.model;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType
@RequiredArgsConstructor
public enum CityClass {
    A(0.5),
    B(0.3),
    C(0.2);

    private final double probability;

    public static CityClass getRandomCityClass() {
        double random = RandomUtils.nextDouble(0, 1);

        if (random < A.probability) {
            return A;
        } else if (random < A.probability + B.probability) {
            return B;
        } else {
            return C;
        }
    }
}
