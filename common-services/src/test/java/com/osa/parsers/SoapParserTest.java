package com.osa.parsers;

import com.osa.TestConfig;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
@TestPropertySource(properties = "charset=UTF-8")
class SoapParserTest extends AbstractParserTest {

    @Autowired
    public SoapParserTest(final NetworkService networkService, final TripService tripService, final SoapParser parser) {
        super(networkService, tripService, parser);
    }

    @Override
    protected String getMockTripsFile() {
        return "mockSoapTrips.xml";
    }

    @Override
    protected String getMockNetworkFile() {
        return "mockSoapNetwork.xml";
    }

    @Override
    protected String getMockOriginStationsFile() {
        return "mockSoapOriginStations.xml";
    }

    @Override
    protected String getMockDestinationStationsFile() {
        return "mockSoapDestinationStations.xml";
    }

}