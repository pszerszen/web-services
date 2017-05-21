package com.osa.client;

import com.osa.parsers.XmlParser;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Component;

@Component
public class XmlCaller extends AbstractRestCaller {

    private static final String ENDPOINT_URL = "http://localhost:8080/rest/xml";

    protected XmlCaller(final XmlParser parser) {
        super(parser);
    }

    @Override
    protected void prepareRequest(final HttpRequest request) {
        request.addHeader("ContentType", getRequestContentType());
    }

    @Override
    protected String getRequestContentType() {
        return "application/xml";
    }

    @Override
    protected String getEndpointUrl() {
        return ENDPOINT_URL;
    }
}