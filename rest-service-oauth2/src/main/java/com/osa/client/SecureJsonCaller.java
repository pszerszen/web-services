package com.osa.client;

import com.osa.client.rest.AbstractAuthenticatedRestCaller;
import com.osa.parsers.JsonParser;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecureJsonCaller extends AbstractAuthenticatedRestCaller {

    @Autowired
    public SecureJsonCaller(final JsonParser parser,
                            @Value("${endpoint.url.json}") String endpointUrl,
                            @Value("${endpoint.url.auth}") String authenticationEndpointUrl) {
        super(parser, endpointUrl, authenticationEndpointUrl);
    }

    @Override
    protected void prepareRequest(final HttpRequest request) {
        super.prepareRequest(request);
        request.addHeader("Content-Type", getRequestContentType());
    }

    @Override
    protected String getRequestContentType() {
        return "application/json";
    }

}
