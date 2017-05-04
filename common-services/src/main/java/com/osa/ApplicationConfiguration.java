package com.osa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.osa.properties.NetworkProperties;
import com.osa.properties.TripProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static com.osa.properties.MinMax.withValues;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@ComponentScan(basePackageClasses = ApplicationConfiguration.class)
@EnableConfigurationProperties
public class ApplicationConfiguration {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
    }

    @Bean
    public TripProperties tripProperties(@Value("${trip.min}") int tripMin, @Value("${trip.max}") int tripMax) {
        return new TripProperties(withValues(tripMin, tripMax));
    }

    @Bean
    public NetworkProperties networkProperties(@Value("${network.cities.min}") int cityMin,
                                               @Value("${network.cities.max}") int cityMax,
                                               @Value("${network.destinations-stations.min}") int destinationStationMin,
                                               @Value("${network.destinations-stations.max}") int destinationStationMax,
                                               @Value("${network.stations.min}") int stationsMin,
                                               @Value("${network.stations.max}") int stationsMax) {
        return NetworkProperties.builder()
                .cities(withValues(cityMin, cityMax))
                .destinationsStations(withValues(destinationStationMin, destinationStationMax))
                .stations(withValues(stationsMin, stationsMax))
                .build();
    }
}
