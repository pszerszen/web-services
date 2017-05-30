package com.osa.client.rest;

import com.osa.client.ResponseWrapper;
import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;

public interface RestServiceCaller {

    ResponseWrapper<Boolean> getHeartBeat();

    ResponseWrapper<Network> getNetwork();

    ResponseWrapper<StationList> getOrigins();

    ResponseWrapper<StationList> getDestinations(String originStation);

    ResponseWrapper<Trip> getTrip(TripRequest request);
}
