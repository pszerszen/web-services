package com.osa.parsers;

import com.osa.TestConfig;
import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.request.TripRequest;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.time.LocalDateTime;

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
    class ParseToSoapMessage implements Parse {
        @Test
        @Override
        public void testParsingTrip() {
            String content = parser.parseToSoapMessage(trip);

            assertNotNull(content);
        }

        @Test
        @Override
        public void testParsingNetwork() {
            String content = parser.parseToSoapMessage(network);

            assertNotNull(content);
        }

        @Test
        @Override
        public void testParsingOriginStations() {
            String content = parser.parseToSoapMessage(departures);

            assertNotNull(content);
        }

        @Test
        @Override
        public void testParsingDestinationStations() {
            String content = parser.parseToSoapMessage(arrivals);

            assertNotNull(content);
        }
    }

    interface Parse {

        void testParsingTrip();

        void testParsingNetwork();

        void testParsingOriginStations();

        void testParsingDestinationStations();
    }

}