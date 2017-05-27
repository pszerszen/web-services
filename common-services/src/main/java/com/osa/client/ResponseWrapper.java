package com.osa.client;

import com.osa.client.rest.model.OAuth2Response;
import lombok.Data;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import java.util.Optional;

@Data
public class ResponseWrapper<T> {
    private long executionTimeInMillis;
    private long requestSize;
    private long responseSize;
    private T response;

    public Header getExecutionTimeInMillisHeader() {
        return new BasicHeader("executionTimeInMillis", Long.toString(executionTimeInMillis));
    }

    public Header getRequestSizeHeader() {
        return new BasicHeader("requestSize", Long.toString(requestSize));
    }

    public Header getresponseSizeHeader() {
        return new BasicHeader("responseSize", Long.toString(responseSize));
    }

    public void addMetrics(ResponseWrapper<?> other) {
        this.executionTimeInMillis += other.executionTimeInMillis;
        this.requestSize += other.requestSize;
        this.responseSize += other.responseSize;
    }

    public static ResponseWrapper<OAuth2Response> fromRequest(HttpUriRequest request) {
        ResponseWrapper<OAuth2Response> wrapper = new ResponseWrapper<>();
        wrapper.setExecutionTimeInMillis(Optional.ofNullable(request.getFirstHeader("executionTimeInMillis"))
                .map(Header::getValue)
                .map(Long::parseLong)
                .orElse(0L));
        wrapper.setRequestSize(Optional.ofNullable(request.getFirstHeader("requestSize"))
                .map(Header::getValue)
                .map(Long::parseLong)
                .orElse(0L));
        wrapper.setResponseSize(Optional.ofNullable(request.getFirstHeader("responseSize"))
                .map(Header::getValue)
                .map(Long::parseLong)
                .orElse(0L));
        request.removeHeaders("executionTimeInMillis");
        request.removeHeaders("requestSize");
        request.removeHeaders("responseSize");
        return wrapper;
    }
}
