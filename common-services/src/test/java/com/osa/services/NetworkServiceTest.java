package com.osa.services;

import com.osa.TestConfig;
import com.osa.model.Network;
import com.osa.model.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitJupiterConfig(TestConfig.class)
class NetworkServiceTest {

    @Autowired
    private NetworkService networkService;

    @Test
    void testGetNetwork() {
        Network network = networkService.getNetwork();

        assertNotNull(network);
    }

    @Test
    void testGetOriginStations() {
        List<Station> originStations = networkService.getOriginStations();

        assertNotNull(originStations);
    }

    @Test
    void testGetDepartureStations() {
        List<Station> departureStations = networkService.getDepartureStations();

        assertNotNull(departureStations);
    }

}