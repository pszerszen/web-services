package com.osa.endpoint;

import com.osa.model.HeartbeatResponse;
import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.request.SimpleRequest;
import com.osa.model.request.TripRequest;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SoapServiceEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final TripService tripService;
    private final NetworkService networkService;

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "heartBeatRequest")
    public HeartbeatResponse heartbeat(@RequestPayload SimpleRequest request) {
        return new HeartbeatResponse();
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "tripRequest")
    public Trip search(@RequestPayload TripRequest request) {
        return tripService.searchTrip(request, null);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "networkRequest")
    public Network getNetwork(@RequestPayload SimpleRequest request) {
        return networkService.getNetwork();
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "originsRequest")
    public StationList getOriginStations(@RequestPayload SimpleRequest request) {
        return networkService.getOriginStations();
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "destinationsRequest")
    public StationList getDestinationStations(@RequestPayload SimpleRequest request) {
        return networkService.getDestinationStations();
    }
}
