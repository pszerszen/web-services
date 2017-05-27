package com.osa.endpoint;

import com.osa.model.DestinationsRequest;
import com.osa.model.HeartBeatRequest;
import com.osa.model.HeartBeatResponse;
import com.osa.model.Network;
import com.osa.model.NetworkRequest;
import com.osa.model.OriginsRequest;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapServiceEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final TripService tripService;
    private final NetworkService networkService;

    @Autowired
    public SoapServiceEndpoint(TripService tripService, NetworkService networkService) {
        this.tripService = tripService;
        this.networkService = networkService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "heartBeatRequest")
    @ResponsePayload
    public HeartBeatResponse heartbeat(@RequestPayload HeartBeatRequest request) {
        return new HeartBeatResponse(true);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "tripRequest")
    public Trip search(@RequestPayload TripRequest request) {
        return tripService.searchTrip(request, null);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "networkRequest")
    public Network getNetwork(@RequestPayload NetworkRequest request) {
        return networkService.getNetwork();
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "originsRequest")
    public StationList getOriginStations(@RequestPayload OriginsRequest request) {
        return networkService.getOriginStations();
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "destinationsRequest")
    public StationList getDestinationStations(@RequestPayload DestinationsRequest request) {
        return networkService.getDestinationStations();
    }
}
