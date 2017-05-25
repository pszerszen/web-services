package com.osa.client;

import com.osa.client.rest.AbstractAuthenticatedRestCaller;
import com.osa.parsers.XmlParser;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XmlCaller extends AbstractAuthenticatedRestCaller {

    private static final String ENDPOINT_URL = "http://localhost:8081/xml/json";
    private static final String AUTHENTICATION_ENDPOINT_URL = "http://localhost:8081/oauth/token";

    @Autowired
    protected XmlCaller(final XmlParser parser) {
        super(parser, ENDPOINT_URL, AUTHENTICATION_ENDPOINT_URL);
    }

    @Override
    protected void prepareRequest(final HttpRequest request) {
        request.addHeader("Content-Type", getRequestContentType());
    }

    @Override
    protected String getRequestContentType() {
        return "application/xml";
    }

}
