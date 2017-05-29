package com.osa.multithread.ws;

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
import com.osa.multithread.MulithreadLoadTest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public abstract class AbstractSoapMultithreadLoadTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int NUMBER_OF_CALLS = 1_000;

    private final SoapClient serviceCaller;
    private final String type;

    @Value("${multithread.calls}")
    private int numberOfCalls;
    @Value("${multithread.threads}")
    private int numberOfThreads;

    public AbstractSoapMultithreadLoadTest(final SoapClient serviceCaller, final String type) {
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
    class HeartbeatTest extends MulithreadLoadTest {

        HeartbeatTest() {
            super(AbstractSoapMultithreadLoadTest.this::heartBeat, type + "-getHeartBeat", numberOfCalls, numberOfThreads);
        }
    }

    @Nested
    class GetNetworkTest extends MulithreadLoadTest {

        GetNetworkTest() {
            super(AbstractSoapMultithreadLoadTest.this::network, type + "-getNetwork", numberOfCalls, numberOfThreads);
        }
    }

    @Nested
    class GetOriginStationsTest extends MulithreadLoadTest {

        GetOriginStationsTest() {
            super(AbstractSoapMultithreadLoadTest.this::origins, type + "-getOrigins", numberOfCalls, numberOfThreads);
        }
    }

    @Nested
    class GetDestinationsStationsTest extends MulithreadLoadTest {

        GetDestinationsStationsTest() {
            super(AbstractSoapMultithreadLoadTest.this::destinations, type + "-getDestinations", numberOfCalls, numberOfThreads);
        }
    }

    @Nested
    class GetTripsTest extends MulithreadLoadTest {

        GetTripsTest() {
            super(AbstractSoapMultithreadLoadTest.this::trips, type + "-getTrip", numberOfCalls, numberOfThreads);
        }
    }
}
