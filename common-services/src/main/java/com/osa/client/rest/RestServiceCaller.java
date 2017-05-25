package com.osa.client.rest;

import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;

public interface RestServiceCaller {

    boolean getHeartBeat();

    Network getNetwork();

    StationList getOrigins();

    StationList getDestinations(String originStation);

    Trip getTrip(TripRequest request);
}
