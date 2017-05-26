package com.osa.client;

import com.osa.TestConfig;
import com.osa.model.Currency;
import com.osa.model.DestinationsRequest;
import com.osa.model.HeartBeatRequest;
import com.osa.model.HeartBeatResponse;
import com.osa.model.Network;
import com.osa.model.NetworkRequest;
import com.osa.model.OriginsRequest;
import com.osa.model.SearchBy;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitJupiterConfig(TestConfig.class)
class WebServiceCallerTest {

    @Value("${network.cities.min}")
    private int cityMin;
    @Value("${network.cities.max}")
    private int cityMax;
    @Value("${network.destinations-stations.min}")
    private int destinationStationMin;
    @Value("${network.destinations-stations.max}")
    private int destinationStationMax;
    @Value("${network.stations.min}")
    private int stationsMin;
    @Value("${network.stations.max}")
    private int stationsMax;
    @Value("${trip.min}")
    private int tripMin;
    @Value("${trip.max}")
    private int tripMax;

    @Autowired
    private WebServiceCaller serviceCaller;

    @Test
    void testGetHeartBeat() {
        HeartBeatResponse heartBeat = serviceCaller.getHeartBeat(new HeartBeatRequest());

        assertTrue(heartBeat.isAlive(), "Expected to have true.");
    }

    @Test
    void testGetNetwork() {
        Network network = serviceCaller.getNetwork(new NetworkRequest());
        assertAll(() -> assertNotNull(network),
                () -> assertNotNull(network.getCities()),
                () -> assertNotNull(network.getStations()),
                () -> assertInRange(cityMin, cityMax, network.getCities().size()),
                () -> assertInRange(stationsMin, stationsMax, network.getStations().size()));
    }

    @Test
    void testGetOrigins() {
        StationList origins = serviceCaller.getOrigins(new OriginsRequest());
        assertAll(
                () -> assertNotNull(origins.getStations()),
                () -> assertInRange(stationsMin, stationsMax, origins.getStations().size()));
    }

    @Test
    void testGetDestinations() {
        StationList destinations = serviceCaller.getDestinations(new DestinationsRequest("ORIGIN STATION"));
        assertAll(
                () -> assertNotNull(destinations.getStations()),
                () -> assertInRange(destinationStationMin, destinationStationMax, destinations.getStations().size()));
    }

    @Test
    void testGetTrip() {
        TripRequest request = TripRequest.builder()
                .searchBy(SearchBy.STATIONS)
                .fromStationId("from station")
                .toStationId("to station")
                .departureDate("02.08.2017")
                .adult(1)
                .children(1)
                .currency(Currency.PLN)
                .build();
        Trip trip = serviceCaller.getTrip(request);
        assertAll(
                () -> assertNotNull(trip.getFrom()),
                () -> assertNotNull(trip.getTo()),
                () -> assertNotNull(trip.getItems()),
                () -> assertInRange(tripMin, tripMax, trip.getItems().size()));
    }

    private static void assertInRange(int min, int max, int value) {
        assertAll(() -> assertTrue(min <= value, () -> "The value: " + value + " is supposed to be bigger than " + min),
                () -> assertTrue(value <= max, "The value: " + value + " is supposed to be smaller than " + max));
    }
}