package com.osa.parsers;

import com.osa.TestConfig;
import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.request.TripRequest;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitJupiterConfig(TestConfig.class)
@TestPropertySource(properties = "charset=UTF-8")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SoapParserTest {

    private final NetworkService networkService;
    private final TripService tripService;
    private final SoapParser parser;

    private Trip trip;
    private Network network;
    private StationList departures;
    private StationList arrivals;

    @BeforeEach
    void setUp() {
        TripRequest request = mock(TripRequest.class);
        when(request.getDepartureDateAsLocalDateTime())
                .thenReturn(LocalDateTime.now().plusDays(1));
        trip = tripService.searchTrip(request, 3);
        network = networkService.getNetwork();
        departures = networkService.getOriginStations();
        arrivals = networkService.getDestinationStations();
    }

    @Nested
    class ParseToSoapMessageTest implements ParseTest {
        @Test
        @Override
        public void testParsingTrip() {
            test(trip);
        }

        @Test
        @Override
        public void testParsingNetwork() {
            test(network);
        }

        @Test
        @Override
        public void testParsingOriginStations() {
            test(departures);
        }

        @Test
        @Override
        public void testParsingDestinationStations() {
            test(arrivals);
        }

        private <T> void test(T object) {
            String content = parser.parseToSoapMessage(object);
            assertNotNull(content);
        }
    }

    @Nested
    class ParseFromSoapMessageTest implements ParseTest {

        @Test
        @Override
        public void testParsingTrip() {
            Trip trip = test("mockTrips.xml", Trip.class);

            assertAll(
                    () -> assertNotNull(trip.getFrom()),
                    () -> assertNotNull(trip.getTo()),
                    () -> assertNotNull(trip.getItems()),
                    () -> assertEquals(3, trip.getItems().size()));
        }

        @Test
        @Override
        public void testParsingNetwork() {
            Network network = test("mockNetwork.xml", Network.class);

            assertAll(
                    () -> assertNotNull(network.getCities()),
                    () -> assertNotNull(network.getStations()),
                    () -> assertEquals(10, network.getCities().size()),
                    () -> assertEquals(10, network.getStations().size()));
        }

        @Test
        @Override
        public void testParsingOriginStations() {
            StationList stationList = test("mockOriginStations.xml", StationList.class);

            assertAll(
                    () -> assertNotNull(stationList.getStationList()),
                    () -> assertEquals(10, stationList.getStationList().size()));
        }

        @Test
        @Override
        public void testParsingDestinationStations() {
            StationList stationList = test("mockDestinationStations.xml", StationList.class);

            assertAll(
                    () -> assertNotNull(stationList.getStationList()),
                    () -> assertEquals(4, stationList.getStationList().size()));
        }

        @SuppressWarnings("unchecked")
        @SneakyThrows
        private <T> T test(String fileName, Class<T> type) {
            URI uri = getClass().getResource(fileName).toURI();
            String content = Files.lines(Paths.get(uri)).collect(joining());

            T response = parser.parseFromSoapMessage(content, type);
            assertNotNull(response);

            return response;
        }
    }
}