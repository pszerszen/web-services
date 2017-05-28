package com.osa.client.rest;

import com.google.common.base.Stopwatch;
import com.osa.client.ResponseWrapper;
import com.osa.client.rest.model.OAuth2Response;
import com.osa.parsers.JsonParser;
import com.osa.parsers.Parser;
import com.osa.util.MetricUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static java.lang.String.join;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Slf4j
@Component
public abstract class AbstractAuthenticatedRestCaller extends AbstractRestCaller {

    private final String authenticationTokenUrl;

    @Value("${oauth.clientId}")
    private String clientId;
    @Value("${oauth.secret}")
    private String secret;

    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;

    @Autowired
    private JsonParser oAuth2Parser;

    protected AbstractAuthenticatedRestCaller(final Parser parser,
                                              final String endpointUrl,
                                              final String authenticationTokenUrl) {
        super(parser, endpointUrl);
        this.authenticationTokenUrl = authenticationTokenUrl;
    }

    @Override
    protected void prepareRequest(HttpRequest request) {
        ResponseWrapper<OAuth2Response> responseWrapper = callForOAuth2Token();
        request.addHeader(responseWrapper.getExecutionTimeInMillisHeader());
        request.addHeader(responseWrapper.getRequestSizeHeader());
        request.addHeader(responseWrapper.getresponseSizeHeader());
        request.addHeader("Authorization", join(SPACE, "Bearer",
                Optional.ofNullable(responseWrapper.getResponse())
                        .map(OAuth2Response::getAccessToken)
                        .orElse(EMPTY)));
    }

    private ResponseWrapper<OAuth2Response> callForOAuth2Token() {
        ResponseWrapper<OAuth2Response> responseWrapper = new ResponseWrapper<>();
        HttpPost request = getAuthenticationRequest();
        responseWrapper.setRequestSize(MetricUtils.counterSizeOfRequest(request));
        HttpEntity entity = null;
        OAuth2Response responseBody = null;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            HttpResponse response = httpClient.execute(request);
            entity = response.getEntity();
            String content = EntityUtils.toString(entity, Charset.forName(charset));
            responseBody = oAuth2Parser.parseFromContent(content, OAuth2Response.class);
            responseWrapper.setResponseSize(MetricUtils.counterSizeOfResponse(response, content));
            responseWrapper.setResponse(responseBody);
            if (StringUtils.isBlank(responseBody.getAccessToken())) {
                throw new AuthenticationException();
            }
        } catch (IOException e) {
            log.error("Error during executing request {}.\n Error: {}", request, e.getStackTrace());
        } catch (AuthenticationException e) {
            log.error("Error while retrieving OAuth2 token: {}", responseBody.getErrorDescription());
        } finally {
            EntityUtils.consumeQuietly(entity);
            responseWrapper.setExecutionTimeInMillis(stopwatch.elapsed(MILLISECONDS));
        }
        return responseWrapper;
    }

    @SneakyThrows
    private HttpPost getAuthenticationRequest() {
        HttpPost request = new HttpPost(authenticationTokenUrl);
        String credentials = join(":", clientId, secret);
        String authenticationHeader = Base64.getEncoder().encodeToString(credentials.getBytes());

        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("Authorization", join(SPACE, "Basic", authenticationHeader));

        List<BasicNameValuePair> postParams = Arrays.asList(
                new BasicNameValuePair("grant_type", "password"),
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", password));
        request.setEntity(new UrlEncodedFormEntity(postParams, charset));
        return request;
    }
}
