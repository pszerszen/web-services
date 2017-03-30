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

    private Cities cities;

    private Stations stations;

    private DestinationsStations destinationsStations;

    public static class Cities extends MinMax {}

    public static class Stations extends MinMax {}

    public static class DestinationsStations extends MinMax {}
}
