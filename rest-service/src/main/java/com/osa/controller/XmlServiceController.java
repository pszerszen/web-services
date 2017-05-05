package com.osa.controller;

import com.osa.services.NetworkService;
import com.osa.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping(path = "/rest/xml", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
public class XmlServiceController extends AbstractServiceController {

    @Autowired
    public XmlServiceController(final TripService tripService, final NetworkService networkService) {
        super(tripService, networkService);
    }
}
