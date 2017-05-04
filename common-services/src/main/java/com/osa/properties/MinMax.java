package com.osa.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinMax {
    protected int min;
    protected int max;

    public static MinMax withValues(int min, int max) {
        return new MinMax(min, max);
    }

    public int getRandom() {
        return RandomUtils.nextInt(min, max + 1);
    }
}
