package com.osa.parsers;

import com.osa.TestConfig;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
@TestPropertySource(properties = "charset=UTF-8")
public class JsonParserTest extends AbstractParserTest {

    @Autowired
    public JsonParserTest(final NetworkService networkService, final TripService tripService, final JsonParser parser) {
        super(networkService, tripService, parser);
    }

    @Override
    protected String getMockTripsFile() {
        return "mockTrips.json";
    }

    @Override
    protected String getMockNetworkFile() {
        return "mockNetwork.json";
    }

    @Override
    protected String getMockOriginStationsFile() {
        return "mockOriginStations.json";
    }

    @Override
    protected String getMockDestinationStationsFile() {
        return "mockDestinationStations.json";
    }

    @Test
    void test() {}
}
