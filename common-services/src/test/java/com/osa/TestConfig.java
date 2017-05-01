package com.osa;

import com.osa.properties.NetworkProperties;
import com.osa.properties.TripProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.osa.properties.MinMax.withValues;

@Configuration
public class TestConfig extends ApplicationConfiguration {

    @Bean
    public TripProperties tripProperties() {
        return new TripProperties(withValues(0,35));
    }

    @Bean
    public NetworkProperties networkProperties() {
        return NetworkProperties.builder()
                .cities(withValues(3000, 3500))
                .destinationsStations(withValues(5, 450))
                .stations(withValues(4200, 5000))
                .build();
    }

}
