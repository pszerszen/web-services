package com.osa.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "network")
public class NetworkProperties {

    private MinMax cities;
    private MinMax stations;
    private MinMax destinationsStations;

}
