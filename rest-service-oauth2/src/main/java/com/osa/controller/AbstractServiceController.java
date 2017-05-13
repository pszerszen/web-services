package com.osa.controller;

import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/oauth2")
public class AbstractServiceController {

    private final TripService tripService;
    private final NetworkService networkService;

    @Autowired
    public AbstractServiceController(TripService tripService, NetworkService networkService) {
        this.tripService = tripService;
        this.networkService = networkService;
    }

    @RequestMapping(path = "/", method = GET)
    public ResponseEntity<Boolean> heartbeat() {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @RequestMapping(path = "/search", method = POST)
    public ResponseEntity<Trip> search(@RequestBody TripRequest request, @RequestParam(required = false) Integer expectedNumber) {
        Trip trip = tripService.searchTrip(request, expectedNumber);
        return ResponseEntity.ok(trip);
    }

    @RequestMapping(path = "/network", method = GET)
    public ResponseEntity<Network> getNetwork() {
        return ResponseEntity.ok(networkService.getNetwork());
    }

    @RequestMapping(path = "/origins", method = GET)
    public ResponseEntity<StationList> getOriginStations() {
        return ResponseEntity.ok(networkService.getOriginStations());
    }

    @SuppressWarnings("unused")
    @RequestMapping(path = "destinations", method = GET)
    public ResponseEntity<StationList> getDestinationStations(@RequestParam String originStation) {
        return ResponseEntity.ok(networkService.getDestinationStations());
    }
}
