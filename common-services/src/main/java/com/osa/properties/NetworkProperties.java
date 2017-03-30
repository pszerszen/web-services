package com.osa.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "network")
public class NetworkProperties {

    private MinMax cities;
    private MinMax stations;
    private MinMax destinationsStations;

}
