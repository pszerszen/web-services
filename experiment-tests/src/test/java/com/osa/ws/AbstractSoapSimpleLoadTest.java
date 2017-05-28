package com.osa.ws;

import com.osa.SimpleLoadTest;
import com.osa.client.ResponseWrapper;
import com.osa.client.ws.SoapClient;
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
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public abstract class AbstractSoapSimpleLoadTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int NUMBER_OF_CALLS = 1_000;

    private final SoapClient serviceCaller;
    private final String type;

    public AbstractSoapSimpleLoadTest(final SoapClient serviceCaller, final String type) {
        this.serviceCaller = serviceCaller;
        this.type = type;
    }

    private ResponseWrapper<HeartBeatResponse> heartBeat() {
        return serviceCaller.getHeartBeat(new HeartBeatRequest());
    }

    private ResponseWrapper<Network> network() {
        return serviceCaller.getNetwork(new NetworkRequest());
    }

    private ResponseWrapper<StationList> origins() {
        return serviceCaller.getOrigins(new OriginsRequest());
    }

    private ResponseWrapper<StationList> destinations() {
        return serviceCaller.getDestinations(new DestinationsRequest(random(6)));
    }

    private ResponseWrapper<Trip> trips() {
        return serviceCaller.getTrip(TripRequest.builder()
                .searchBy(SearchBy.STATIONS)
                .fromStationId(random(10))
                .toStationId(random(10))
                .departureDate(LocalDate.now().plusDays(RandomUtils.nextLong(1, 366)).format(DATE_TIME_FORMATTER))
                .adult(nextInt(1, 6))
                .children(nextInt(0, 6))
                .currency(Currency.getRandom())
                .build());
    }

    @Test
    void ignore() {
    }

    @Nested
    class HeartbeatTest extends SimpleLoadTest {

        HeartbeatTest() {
            super(AbstractSoapSimpleLoadTest.this::heartBeat, type + "-getHeartBeat", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetNetworkTest extends SimpleLoadTest {

        GetNetworkTest() {
            super(AbstractSoapSimpleLoadTest.this::network, type + "-getNetwork", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetOriginStationsTest extends SimpleLoadTest {

        GetOriginStationsTest() {
            super(AbstractSoapSimpleLoadTest.this::origins, type + "-getOrigins", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetDestinationsStationsTest extends SimpleLoadTest {

        GetDestinationsStationsTest() {
            super(AbstractSoapSimpleLoadTest.this::destinations, type + "-getDestinations", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetTripsTest extends SimpleLoadTest {

        GetTripsTest() {
            super(AbstractSoapSimpleLoadTest.this::trips, type + "-getTrip", NUMBER_OF_CALLS);
        }
    }
}
