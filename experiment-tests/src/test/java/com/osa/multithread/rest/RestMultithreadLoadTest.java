package com.osa.multithread.rest;

import com.osa.client.ResponseWrapper;
import com.osa.client.rest.RestServiceCaller;
import com.osa.model.Currency;
import com.osa.model.SearchBy;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.multithread.MulithreadLoadTest;
import com.osa.properties.Method;
import com.osa.properties.TestMethodProperties;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static com.osa.Constansts.DATE_TIME_FORMATTER;
import static com.osa.properties.Method.getDestinations;
import static com.osa.properties.Method.getNetwork;
import static com.osa.properties.Method.getOrigins;
import static com.osa.properties.Method.heartbeat;
import static com.osa.properties.Method.searchTrip;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

public abstract class RestMultithreadLoadTest {

    private final RestServiceCaller serviceCaller;
    private final String type;
    private final Map<Method, TestMethodProperties> properties;

    protected RestMultithreadLoadTest(final RestServiceCaller serviceCaller, final Map<Method, TestMethodProperties> properties) {
        this.serviceCaller = serviceCaller;
        type = substringBeforeLast(serviceCaller.getClass().getSimpleName(), "Caller");
        this.properties = properties;
    }

    private ResponseWrapper<Trip> tripCall() {
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

    private ResponseWrapper<StationList> destinationsCall() {
        return serviceCaller.getDestinations(random(6));
    }

    @Test
    void ignore() {
    }

    @Nested
    class GetNetworkTest extends MulithreadLoadTest {

        GetNetworkTest() {
            super(serviceCaller::getNetwork, type + "-getNetwork", properties.get(getNetwork));
        }
    }

    @Nested
    class GetOriginStationsTest extends MulithreadLoadTest {

        GetOriginStationsTest() {
            super(serviceCaller::getOrigins, type + "-getOrigins", properties.get(getOrigins));
        }
    }

    @Nested
    class GetTripsTest extends MulithreadLoadTest {

        GetTripsTest() {
            super(RestMultithreadLoadTest.this::tripCall, type + "-getTrip", properties.get(searchTrip));
        }
    }

    @Nested
    class GetDestinationsStationsTest extends MulithreadLoadTest {

        GetDestinationsStationsTest() {
            super(RestMultithreadLoadTest.this::destinationsCall, type + "-getDestinations", properties.get(getDestinations));
        }
    }

    @Nested
    class HeartbeatTest extends MulithreadLoadTest {

        HeartbeatTest() {
            super(serviceCaller::getHeartBeat, type + "-getHeartBeat", properties.get(heartbeat));
        }
    }
}
