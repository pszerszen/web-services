package com.osa.parsers;

import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class AbstractParserTest {

    private final NetworkService networkService;
    private final TripService tripService;
    private final Parser parser;

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

    private <T> void testParseTo(T object) {
        String content = parser.parseToContent(object);
        assertNotNull(content);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private <T> T testParseFrom(String fileName, Class<T> type) {
        URI uri = getClass().getResource(fileName).toURI();
        String content = Files.lines(Paths.get(uri)).collect(joining());

        T response = parser.parseFromContent(content, type);
        assertNotNull(response);

        return response;
    }

    @Test
    void test() {}

    @Nested
    protected class ParseToTest implements ParseTest {
        @Test
        @Override
        public void testParsingTrip() {
            testParseTo(trip);
        }

        @Test
        @Override
        public void testParsingNetwork() {
            testParseTo(network);
        }

        @Test
        @Override
        public void testParsingOriginStations() {
            testParseTo(departures);
        }

        @Test
        @Override
        public void testParsingDestinationStations() {
            testParseTo(arrivals);
        }
    }

    protected abstract String getMockTripsFile();

    protected abstract String getMockNetworkFile();

    protected abstract String getMockOriginStationsFile();

    protected abstract String getMockDestinationStationsFile();

    @Nested
    protected class ParseFromTest implements ParseTest {

        @Test
        @Override
        public void testParsingTrip() {
            Trip trip = testParseFrom(getMockTripsFile(), Trip.class);

            assertAll(
                    () -> assertNotNull(trip.getFrom()),
                    () -> assertNotNull(trip.getTo()),
                    () -> assertNotNull(trip.getItems()),
                    () -> assertEquals(3, trip.getItems().size()));
        }

        @Test
        @Override
        public void testParsingNetwork() {
            Network network = testParseFrom(getMockNetworkFile(), Network.class);

            assertAll(
                    () -> assertNotNull(network.getCities()),
                    () -> assertNotNull(network.getStations()),
                    () -> assertEquals(10, network.getCities().size()),
                    () -> assertEquals(10, network.getStations().size()));
        }

        @Test
        @Override
        public void testParsingOriginStations() {
            StationList stationList = testParseFrom(getMockOriginStationsFile(), StationList.class);

            assertAll(
                    () -> assertNotNull(stationList.getStationList()),
                    () -> assertEquals(10, stationList.getStationList().size()));
        }

        @Test
        @Override
        public void testParsingDestinationStations() {
            StationList stationList = testParseFrom(getMockDestinationStationsFile(), StationList.class);

            assertAll(
                    () -> assertNotNull(stationList.getStationList()),
                    () -> assertEquals(4, stationList.getStationList().size()));
        }
    }
}
