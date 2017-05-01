package com.osa.services;

import com.osa.TestConfig;
import com.osa.model.Network;
import com.osa.model.StationList;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitJupiterConfig(TestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class NetworkServiceTest {

    private final NetworkService networkService;

    @Test
    void testGetNetwork() {
        Network network = networkService.getNetwork();

        assertNotNull(network);
    }

    @Test
    void testGetOriginStations() {
        StationList originStations = networkService.getOriginStations();

        assertNotNull(originStations);
    }

    @Test
    void testGetDestinationStations() {
        StationList departureStations = networkService.getDestinationStations();

        assertNotNull(departureStations);
    }

}