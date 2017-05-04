package com.osa.properties;

import lombok.Data;

@Data
public class TripProperties extends MinMax {

    public TripProperties(MinMax minMax) {
        super();
        min = minMax.min;
        max = minMax.max;
    }
}
