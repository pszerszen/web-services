package com.osa.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "trip")
public class TripProperties extends MinMax {

    public TripProperties(MinMax minMax) {
        super();
        setMin(minMax.getMin());
        setMax(minMax.getMax());
    }
}
