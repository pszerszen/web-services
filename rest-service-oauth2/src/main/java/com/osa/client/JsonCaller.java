package com.osa.client;

import com.osa.client.rest.AbstractAuthenticatedRestCaller;
import com.osa.parsers.JsonParser;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonCaller extends AbstractAuthenticatedRestCaller {

    private static final String ENDPOINT_URL = "http://localhost:8081/rest/json";
    private static final String AUTHENTICATION_ENDPOINT_URL = "http://localhost:8081/oauth/token";

    @Autowired
    protected JsonCaller(final JsonParser parser) {
        super(parser, ENDPOINT_URL, AUTHENTICATION_ENDPOINT_URL);
    }

    @Override
    protected void prepareRequest(final HttpRequest request) {
        request.addHeader("ContentType", getRequestContentType());
    }

    @Override
    protected String getRequestContentType() {
        return "application/json";
    }

}
