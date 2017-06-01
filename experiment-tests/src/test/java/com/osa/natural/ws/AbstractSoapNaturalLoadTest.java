package com.osa.natural.ws;

import com.osa.ResponseWrapperSupplier;
import com.osa.client.ws.SoapClient;
import com.osa.model.Currency;
import com.osa.model.DestinationsRequest;
import com.osa.model.HeartBeatRequest;
import com.osa.model.NetworkRequest;
import com.osa.model.OriginsRequest;
import com.osa.model.SearchBy;
import com.osa.model.TripRequest;
import com.osa.natural.NaturalLoadTest;
import com.osa.properties.Method;
import com.osa.properties.TestMethodProperties;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.util.Map;

import static com.osa.Constansts.DATE_TIME_FORMATTER;
import static com.osa.properties.Method.any;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public abstract class AbstractSoapNaturalLoadTest extends NaturalLoadTest {

    private final SoapClient serviceCaller;

    public AbstractSoapNaturalLoadTest(final SoapClient serviceCaller, final String name, final Map<Method, TestMethodProperties> properties) {
        super(name, properties.get(any));
        this.serviceCaller = serviceCaller;
    }

    @Override
    protected ResponseWrapperSupplier heartBeat() {
        return () -> serviceCaller.getHeartBeat(new HeartBeatRequest());
    }

    @Override
    protected ResponseWrapperSupplier network() {
        return () -> serviceCaller.getNetwork(new NetworkRequest());
    }

    @Override
    protected ResponseWrapperSupplier origins() {
        return () -> serviceCaller.getOrigins(new OriginsRequest());
    }

    @Override
    protected ResponseWrapperSupplier destinations() {
        return () -> serviceCaller.getDestinations(new DestinationsRequest(random(6)));
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
