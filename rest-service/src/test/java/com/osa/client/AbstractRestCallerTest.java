package com.osa.client;

import com.osa.client.rest.AbstractRestCaller;
import com.osa.model.Currency;
import com.osa.model.Network;
import com.osa.model.SearchBy;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class AbstractRestCallerTest {

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

    private final AbstractRestCaller restCaller;

    protected AbstractRestCallerTest(final AbstractRestCaller restCaller) {
        this.restCaller = restCaller;
    }

    @Test
    @DisplayName("Is Heartbeat working")
    protected void testHeartBeat() {
        boolean heartBeat = restCaller.getHeartBeat();
        assertTrue(heartBeat, "Expected to have true.");
    }

    @Test
    @DisplayName("Can download Network")
    protected void testGetNetwork() {
        Network network = restCaller.getNetwork();
        assertAll(() -> assertNotNull(network),
                () -> assertNotNull(network.getCities()),
                () -> assertNotNull(network.getStations()),
                () -> assertInRange(cityMin, cityMax, network.getCities().size()),
                () -> assertInRange(stationsMin, stationsMax, network.getStations().size()));
    }

    @Test
    @DisplayName("Can download origin stations")
    protected void testGetOrigins() {
        StationList origins = restCaller.getOrigins();
        assertAll(
                () -> assertNotNull(origins.getStations()),
                () -> assertInRange(stationsMin, stationsMax, origins.getStations().size()));
    }

    @Test
    @DisplayName("Can download destinations stations")
    protected void testGetDestinations() {
        StationList destinations = restCaller.getDestinations("DESTINATION");
        assertAll(
                () -> assertNotNull(destinations.getStations()),
                () -> assertInRange(destinationStationMin, destinationStationMax, destinations.getStations().size()));
    }

    @Test
    @DisplayName("Can find trips")
    protected void testGetTrips() {
        TripRequest request = TripRequest.builder()
                .searchBy(SearchBy.STATIONS)
                .fromStationId("from station")
                .toStationId("to station")
                .departureDate("02.08.2017")
                .adult(1)
                .children(1)
                .currency(Currency.PLN)
                .build();
        Trip trip = restCaller.getTrip(request);
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
