package com.osa.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TripProperties extends MinMax {

    public TripProperties(MinMax minMax) {
        super();
        min = minMax.min;
        max = minMax.max;
    }
}
