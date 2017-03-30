package com.osa.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;

@Getter
@Setter
public class MinMax {
    private int min;
    private int max;

    public int getRandom() {
        return RandomUtils.nextInt(min, max + 1);
    }
}
