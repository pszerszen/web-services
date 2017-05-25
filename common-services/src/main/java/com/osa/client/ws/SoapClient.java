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
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapClient extends WebServiceGatewaySupport {

    HeartBeatResponse getHeartBeat(HeartBeatRequest request) {
        return marshalSendAndReceive(request);
    }

    Network getNetwork(NetworkRequest request) {
        return marshalSendAndReceive(request);
    }

    StationList getOrigins(OriginsRequest request) {
        return marshalSendAndReceive(request);
    }

    StationList getDestinations(DestinationsRequest request) {
        return marshalSendAndReceive(request);
    }

    Trip getTrip(TripRequest request) {
        return marshalSendAndReceive(request);
    }

    @SuppressWarnings("unchecked")
    private <Request, Response> Response marshalSendAndReceive(Request reqest) {
        return (Response) getWebServiceTemplate().marshalSendAndReceive(reqest);
    }
}
