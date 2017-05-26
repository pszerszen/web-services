package com.osa.client;

import com.osa.client.ws.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebServiceCaller extends SoapClient {

    @Autowired
    public WebServiceCaller(@Value("${endpoint.url.soap}") String endpointUrl) {
        super(endpointUrl);
    }
}
