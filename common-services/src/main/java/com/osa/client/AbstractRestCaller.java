package com.osa.client;

import com.osa.model.Network;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.parsers.Parser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public abstract class AbstractRestCaller implements ServiceCaller {

    @Value("${charset}")
    private String charset;

    private final HttpClient httpClient;
    private final Parser parser;

    protected AbstractRestCaller(final Parser parser) {
        this.parser = parser;
        this.httpClient = HttpClientBuilder.create().build();
    }

    protected abstract void prepareRequest(HttpRequest request);

    protected abstract String getRequestContentType();
    protected abstract String getEndpointUrl();

    @Override
    public boolean getHeartBeat() {
        HttpGet request = new HttpGet(RestEndpointUri.heartbeat.getUrl(getEndpointUrl()));
        prepareRequest(request);
        HttpEntity entity = null;
        try {
            HttpResponse response = httpClient.execute(request);
            entity = response.getEntity();
            String content = EntityUtils.toString(entity, Charset.forName(charset));
            return Boolean.parseBoolean(content);
        } catch (IOException e) {
            log.error("Error during executing request {}.\n Error: {}", request, e.getStackTrace());
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
        return false;
    }

    @Override
    public Network getNetwork() {
        HttpGet request = new HttpGet(RestEndpointUri.network.getUrl(getEndpointUrl()));
        prepareRequest(request);
        return executeRequest(request, Network.class);
    }

    @Override
    public StationList getOrigins() {
        HttpGet request = new HttpGet(RestEndpointUri.origins.getUrl(getEndpointUrl()));
        prepareRequest(request);
        return executeRequest(request, StationList.class);
    }

    @Override
    @SneakyThrows
    public StationList getDestinations(final String originStation) {
        String url = new URIBuilder(RestEndpointUri.origins.getUrl(getEndpointUrl()))
                .addParameter("originStation", originStation)
                .build().toString();
        HttpGet request = new HttpGet(url);
        prepareRequest(request);
        return executeRequest(request, StationList.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Trip getTrip(final TripRequest tripRequest) {
        HttpPost request = new HttpPost(RestEndpointUri.search.getUrl(getEndpointUrl()));
        prepareRequest(request);
        byte[] content = parser.parseToContent(tripRequest).getBytes(Charset.forName(charset));
        BasicHttpEntity postEntity = new BasicHttpEntity();
        postEntity.setContentType(getRequestContentType());
        postEntity.setContent(new ByteArrayInputStream(content));
        postEntity.setContentLength(Integer.valueOf(content.length).longValue());
        request.setEntity(postEntity);
        return executeRequest(request, Trip.class);
    }

    private <T> T executeRequest(HttpUriRequest request, Class<T> responseType) {
        HttpEntity entity = null;
        try {
            HttpResponse response = httpClient.execute(request);
            entity = response.getEntity();
            String content = EntityUtils.toString(entity, Charset.forName(charset));
            return parser.parseFromContent(content, responseType);
        } catch (IOException e) {
            log.error("Error during executing request {}.\n Error: {}", request, e.getStackTrace());
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
        return null;
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
