package com.osa.controller;

import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/rest/json", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class JsonServiceController extends AbstractServiceController {

    @Autowired
    public JsonServiceController(final TripService tripService, final NetworkService networkService) {
        super(tripService, networkService);
    }
}
