package com.osa.util;

import lombok.experimental.UtilityClass;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import static java.lang.String.join;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;

@UtilityClass
public class MetricUtils {

    private static final String HEADER_DELIMITER = ": ";

    public long counterSizeOfRequest(HttpUriRequest request) {
        long size = new StringBuilder(request.getURI().toString())
                .append(request.getMethod())
                .append(stringifyHeaders(request.getAllHeaders()))
                .toString().getBytes().length;

        if(request instanceof HttpPost) {
            HttpPost httpPost = (HttpPost) request;
            size += httpPost.getEntity().getContentLength();
        }

        return size;
    }

    public long counterSizeOfResponse(HttpResponse response) {
        return stringifyHeaders(response.getAllHeaders()).getBytes().length +
                response.getEntity().getContentLength();
    }

    private String stringifyHeaders(Header... headers) {
        return stream(headers)
                .map(header -> join(HEADER_DELIMITER, header.getName(), header.getValue()))
                .collect(joining(SPACE));
    }
}
