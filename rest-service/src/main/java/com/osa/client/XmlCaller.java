package com.osa.client;

import com.osa.client.rest.AbstractRestCaller;
import com.osa.parsers.XmlParser;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class XmlCaller extends AbstractRestCaller {

    public XmlCaller(final XmlParser parser, @Value("${endpoint.url.xml}") String endpointUrl) {
        super(parser, endpointUrl);
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
