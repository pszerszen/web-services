package com.osa.properties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NetworkProperties {

    private MinMax cities;
    private MinMax stations;
    private MinMax destinationsStations;

}
