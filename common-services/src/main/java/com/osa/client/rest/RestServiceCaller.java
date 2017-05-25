package com.osa.client.rest;

import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;

import java.io.UnsupportedEncodingException;

public interface RestServiceCaller {

    boolean getHeartBeat() throws UnsupportedEncodingException;

    Network getNetwork() throws UnsupportedEncodingException;

    StationList getOrigins() throws UnsupportedEncodingException;

    StationList getDestinations(String originStation) throws UnsupportedEncodingException;

    Trip getTrip(TripRequest request) throws UnsupportedEncodingException;
}
