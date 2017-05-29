package com.osa.simple.rest;

import com.osa.simple.SimpleLoadTest;
import com.osa.client.ResponseWrapper;
import com.osa.client.rest.RestServiceCaller;
import com.osa.model.Currency;
import com.osa.model.SearchBy;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

public abstract class RestSimpleLoadTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final RestServiceCaller serviceCaller;
    private final String type;

    @Value("${simple.calls}")
    private int numberOfCalls;

    protected RestSimpleLoadTest(final RestServiceCaller serviceCaller) {
        this.serviceCaller = serviceCaller;
        type = substringBeforeLast(serviceCaller.getClass().getSimpleName(), "Caller");
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
    class HeartbeatTest extends SimpleLoadTest {

        HeartbeatTest() {
            super(serviceCaller::getHeartBeat, type + "-getHeartBeat", numberOfCalls);
        }
    }

    @Nested
    class GetNetworkTest extends SimpleLoadTest {

        GetNetworkTest() {
            super(serviceCaller::getNetwork, type + "-getNetwork", numberOfCalls);
        }
    }

    @Nested
    class GetOriginStationsTest extends SimpleLoadTest {

        GetOriginStationsTest() {
            super(serviceCaller::getOrigins, type + "-getOrigins", numberOfCalls);
        }
    }

    @Nested
    class GetDestinationsStationsTest extends SimpleLoadTest {

        GetDestinationsStationsTest() {
            super(RestSimpleLoadTest.this::destinationsCall, type + "-getDestinations", numberOfCalls);
        }
    }

    @Nested
    class GetTripsTest extends SimpleLoadTest {

        GetTripsTest() {
            super(RestSimpleLoadTest.this::tripCall, type + "-getTrip", numberOfCalls);
        }
    }
}
