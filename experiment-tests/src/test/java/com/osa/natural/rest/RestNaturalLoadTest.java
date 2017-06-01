package com.osa.natural.rest;

import com.osa.ResponseWrapperSupplier;
import com.osa.client.rest.RestServiceCaller;
import com.osa.model.Currency;
import com.osa.model.SearchBy;
import com.osa.model.TripRequest;
import com.osa.natural.NaturalLoadTest;
import com.osa.properties.Method;
import com.osa.properties.TestMethodProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.util.Map;

import static com.osa.Constansts.DATE_TIME_FORMATTER;
import static com.osa.properties.Method.any;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

@Slf4j
public abstract class RestNaturalLoadTest extends NaturalLoadTest {

    private final RestServiceCaller serviceCaller;

    public RestNaturalLoadTest(final RestServiceCaller serviceCaller, final Map<Method, TestMethodProperties> properties) {
        super(substringBeforeLast(serviceCaller.getClass().getSimpleName(), "Caller"), properties.get(any));
        this.serviceCaller = serviceCaller;
    }

    @Override
    protected ResponseWrapperSupplier heartBeat() {
        return serviceCaller::getNetwork;
    }

    @Override
    protected ResponseWrapperSupplier network() {
        return serviceCaller::getNetwork;
    }

    @Override
    protected ResponseWrapperSupplier origins() {
        return serviceCaller::getOrigins;
    }

    @Override
    protected ResponseWrapperSupplier destinations() {
        return () -> serviceCaller.getDestinations(random(6));
    }

    @Override
    protected ResponseWrapperSupplier trip() {
        return () -> serviceCaller.getTrip(TripRequest.builder()
                .searchBy(SearchBy.STATIONS)
                .fromStationId(random(10))
                .toStationId(random(10))
                .departureDate(LocalDate.now().plusDays(RandomUtils.nextLong(1, 366)).format(DATE_TIME_FORMATTER))
                .adult(nextInt(1, 6))
                .children(nextInt(0, 6))
                .currency(Currency.getRandom())
                .build());
    }
}
