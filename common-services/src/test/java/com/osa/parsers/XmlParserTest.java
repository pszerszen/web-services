package com.osa.parsers;

import com.osa.TestConfig;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
@TestPropertySource(properties = "charset=UTF-8")
public class XmlParserTest extends AbstractParserTest {

    @Autowired
    public XmlParserTest(final NetworkService networkService, final TripService tripService, final XmlParser parser) {
        super(networkService, tripService, parser);
    }

    @Override
    protected String getMockTripsFile() {
        return "mockTrips.xml";
    }

    @Override
    protected String getMockNetworkFile() {
        return "mockNetwork.xml";
    }

    @Override
    protected String getMockOriginStationsFile() {
        return "mockOriginStations.xml";
    }

    @Override
    protected String getMockDestinationStationsFile() {
        return "mockDestinationStations.xml";
    }

}
