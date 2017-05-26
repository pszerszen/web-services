package com.osa.client.ws;

import com.osa.model.DestinationsRequest;
import com.osa.model.HeartBeatRequest;
import com.osa.model.HeartBeatResponse;
import com.osa.model.Network;
import com.osa.model.NetworkRequest;
import com.osa.model.OriginsRequest;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

public class SoapClient extends WebServiceGatewaySupport {

    public SoapClient(String endpointUrl, Jaxb2Marshaller marshaller, ClientInterceptor... interceptors) {
        super();
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setDefaultUri(endpointUrl);
        setInterceptors(interceptors);
    }

    public HeartBeatResponse getHeartBeat(HeartBeatRequest request) {
        return marshalSendAndReceive(request);
    }

    public Network getNetwork(NetworkRequest request) {
        return marshalSendAndReceive(request);
    }

    public StationList getOrigins(OriginsRequest request) {
        return marshalSendAndReceive(request);
    }

    public StationList getDestinations(DestinationsRequest request) {
        return marshalSendAndReceive(request);
    }

    public Trip getTrip(TripRequest request) {
        return marshalSendAndReceive(request);
    }

    @SuppressWarnings("unchecked")
    private <Request, Response> Response marshalSendAndReceive(Request request) {
        return (Response) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
