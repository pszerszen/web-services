package com.osa.model.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RandomValuesUtils {

    public String randomName() {
        int length = RandomUtils.nextInt(5, 20);
        return RandomStringUtils.randomAlphabetic(length);
    }

    public String randomAddress() {
        int length = RandomUtils.nextInt(15, 35);
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public List<Long> randomStationsIndexes() {
        int length = RandomUtils.nextInt(1, 11);
        return randomIndexesList(length);
    }

    public List<Long> randomDestinationCities() {
        int length = RandomUtils.nextInt(1, 400);
        return randomIndexesList(length);
    }

    private List<Long> randomIndexesList(int length) {
        return IntStream.range(0, length)
                .mapToLong(i -> RandomUtils.nextLong())
                .distinct().boxed()
                .collect(Collectors.toList());
    }
}
