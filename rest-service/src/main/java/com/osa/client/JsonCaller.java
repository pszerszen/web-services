package com.osa.client;

import com.osa.parsers.JsonParser;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JsonCaller extends AbstractRestCaller {

    private static final String ENDPOINT_URL = "http://localhost:8080/rest/json";

    protected JsonCaller(final JsonParser parser) {
        super(parser, ENDPOINT_URL);
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
