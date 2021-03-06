package com.osa.client.rest;

import com.google.common.base.Stopwatch;
import com.osa.client.ResponseWrapper;
import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.parsers.Parser;
import com.osa.util.MetricUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Component
public abstract class AbstractRestCaller implements RestServiceCaller {

    @Value("${charset}")
    protected String charset;

    private final Parser parser;
    private final String endpointUrl;

    protected AbstractRestCaller(final Parser parser, final String endpointUrl) {
        this.parser = parser;
        this.endpointUrl = endpointUrl;
    }

    protected abstract void prepareRequest(HttpRequest request);

    protected abstract String getRequestContentType();

    @Override
    public ResponseWrapper<Boolean> getHeartBeat() {
        ResponseWrapper<Boolean> responseWrapper = new ResponseWrapper<>();
        HttpEntity entity = null;
        HttpGet request = new HttpGet(RestEndpointUri.heartbeat.getUrl(endpointUrl));
        prepareRequest(request);
        responseWrapper.setRequestSize(MetricUtils.counterSizeOfRequest(request));
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            HttpResponse response = httpClient().execute(request);
            entity = response.getEntity();
            String content = EntityUtils.toString(entity, Charset.forName(charset));
            responseWrapper.setResponseSize(MetricUtils.counterSizeOfResponse(response, content));
            responseWrapper.setResponse(Boolean.parseBoolean(content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            EntityUtils.consumeQuietly(entity);
            responseWrapper.setExecutionTimeInMillis(stopwatch.elapsed(MILLISECONDS));
        }

        responseWrapper.addMetrics(ResponseWrapper.fromRequest(request));
        return responseWrapper;
    }

    @Override
    public ResponseWrapper<Network> getNetwork() {
        HttpGet request = new HttpGet(RestEndpointUri.network.getUrl(endpointUrl));
        prepareRequest(request);
        return executeRequest(request, Network.class);
    }

    @Override
    public ResponseWrapper<StationList> getOrigins() {
        HttpGet request = new HttpGet(RestEndpointUri.origins.getUrl(endpointUrl));
        prepareRequest(request);
        return executeRequest(request, StationList.class);
    }

    @Override
    @SneakyThrows
    public ResponseWrapper<StationList> getDestinations(final String originStation) {
        String url = new URIBuilder(RestEndpointUri.destinations.getUrl(endpointUrl))
                .addParameter("originStation", originStation)
                .build().toString();
        HttpGet request = new HttpGet(url);
        prepareRequest(request);
        return executeRequest(request, StationList.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseWrapper<Trip> getTrip(final TripRequest tripRequest) {
        HttpPost request = new HttpPost(RestEndpointUri.search.getUrl(endpointUrl));
        prepareRequest(request);
        byte[] content = parser.parseToContent(tripRequest).getBytes(Charset.forName(charset));
        BasicHttpEntity postEntity = new BasicHttpEntity();
        postEntity.setContentType(getRequestContentType());
        postEntity.setContent(new ByteArrayInputStream(content));
        postEntity.setContentLength(Integer.valueOf(content.length).longValue());
        request.setEntity(postEntity);
        return executeRequest(request, Trip.class);
    }

    private <T> ResponseWrapper<T> executeRequest(HttpUriRequest request, Class<T> responseType) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setRequestSize(MetricUtils.counterSizeOfRequest(request));
        HttpEntity entity = null;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            HttpResponse response = httpClient().execute(request);
            entity = response.getEntity();
            String content = EntityUtils.toString(entity, Charset.forName(charset));
            responseWrapper.setResponseSize(MetricUtils.counterSizeOfResponse(response, content));
            responseWrapper.setResponse(parser.parseFromContent(content, responseType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            EntityUtils.consumeQuietly(entity);
            responseWrapper.setExecutionTimeInMillis(stopwatch.elapsed(MILLISECONDS));
        }
        responseWrapper.addMetrics(ResponseWrapper.fromRequest(request));
        return responseWrapper;
    }

    protected HttpClient httpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(getRequestConfig((int) MINUTES.toMillis(3)))
                .setDefaultSocketConfig(getSocketConfig((int) MINUTES.toMillis(3)))
                .build();
    }

    private static RequestConfig getRequestConfig(int timeoutMillis) {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(timeoutMillis)
                .setConnectTimeout(timeoutMillis)
                .setSocketTimeout(timeoutMillis)
                .build();
    }

    private static SocketConfig getSocketConfig(int timeoutMillis) {
        return SocketConfig.custom()
                .setSoTimeout(timeoutMillis)
                .build();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private enum RestEndpointUri {
        heartbeat(""),
        search("search"),
        network("network"),
        origins("origins"),
        destinations("destinations");

        private final String uri;

        private String getUrl(String endpointUrl) {
            return String.join("/", endpointUrl, uri);
        }
    }
}
