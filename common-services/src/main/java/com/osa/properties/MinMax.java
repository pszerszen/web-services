package com.osa.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

@Data
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
