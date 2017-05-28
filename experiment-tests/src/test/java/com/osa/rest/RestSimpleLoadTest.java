package com.osa.rest;

import com.osa.SimpleLoadTest;
import com.osa.client.ResponseWrapper;
import com.osa.client.rest.RestServiceCaller;
import com.osa.model.Currency;
import com.osa.model.SearchBy;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

public abstract class RestSimpleLoadTest {

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int NUMBER_OF_CALLS = 100_000;

    private final RestServiceCaller serviceCaller;
    private final String type;

    RestSimpleLoadTest(final RestServiceCaller serviceCaller) {
        this.serviceCaller = serviceCaller;
        type = substringBeforeLast(serviceCaller.getClass().getSimpleName(), "Caller");
    }

    private ResponseWrapper<Trip> performATripCall() {
        TripRequest request = TripRequest.builder()
                .searchBy(SearchBy.STATIONS)
                .fromStationId(random(10))
                .toStationId(random(10))
                .departureDate(randomDate())
                .adult(nextInt(1, 6))
                .children(nextInt(0, 6))
                .currency(Currency.getRandom())
                .build();
        return serviceCaller.getTrip(request);
    }

    private String randomDate() {
        return LocalDate.now().plusDays(RandomUtils.nextLong(1, 366)).format(DATE_TIME_FORMATTER);
    }

    @Nested
    class HearbeatTest extends SimpleLoadTest {

        HearbeatTest() {
            super(serviceCaller::getHeartBeat, type + "::getHeartBeat", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetNetworkTest extends SimpleLoadTest {

        GetNetworkTest() {
            super(serviceCaller::getNetwork, type + "::getNetwork", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetOriginStationsTest extends SimpleLoadTest {

        GetOriginStationsTest() {
            super(serviceCaller::getOrigins, type + "::getOrigins", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetDestinationsStationsTest extends SimpleLoadTest {

        GetDestinationsStationsTest() {
            super(() -> serviceCaller.getDestinations(random(6)), type + "::getDestinations", NUMBER_OF_CALLS);
        }
    }

    @Nested
    class GetTripsTest extends SimpleLoadTest {

        GetTripsTest() {
            super(RestSimpleLoadTest.this::performATripCall, type + "::getTrip", NUMBER_OF_CALLS);
        }
    }
}
