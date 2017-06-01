package com.osa.simple.rest;

import com.osa.client.ResponseWrapper;
import com.osa.client.rest.RestServiceCaller;
import com.osa.model.Currency;
import com.osa.model.SearchBy;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.properties.Method;
import com.osa.properties.TestMethodProperties;
import com.osa.simple.SimpleLoadTest;
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

public abstract class RestSimpleLoadTest {

    private final RestServiceCaller serviceCaller;
    private final String type;

    private final Map<Method, TestMethodProperties> properties;

    protected RestSimpleLoadTest(final RestServiceCaller serviceCaller, final Map<Method, TestMethodProperties> properties) {
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

    private int numberOfCallsForMethod(Method method) {
        return properties.get(method).getCalls();
    }

    @Test
    void ignore() {
    }

    @Nested
    class HeartbeatTest extends SimpleLoadTest {

        HeartbeatTest() {
            super(serviceCaller::getHeartBeat, type + "-getHeartBeat", numberOfCallsForMethod(heartbeat));
        }
    }

    @Nested
    class GetNetworkTest extends SimpleLoadTest {

        GetNetworkTest() {
            super(serviceCaller::getNetwork, type + "-getNetwork", numberOfCallsForMethod(getNetwork));
        }
    }

    @Nested
    class GetOriginStationsTest extends SimpleLoadTest {

        GetOriginStationsTest() {
            super(serviceCaller::getOrigins, type + "-getOrigins", numberOfCallsForMethod(getOrigins));
        }
    }

    @Nested
    class GetDestinationsStationsTest extends SimpleLoadTest {

        GetDestinationsStationsTest() {
            super(RestSimpleLoadTest.this::destinationsCall, type + "-getDestinations", numberOfCallsForMethod(getDestinations));
        }
    }

    @Nested
    class GetTripsTest extends SimpleLoadTest {

        GetTripsTest() {
            super(RestSimpleLoadTest.this::tripCall, type + "-getTrip", numberOfCallsForMethod(searchTrip));
        }
    }
}
