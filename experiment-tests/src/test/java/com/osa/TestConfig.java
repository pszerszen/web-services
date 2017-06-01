package com.osa;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.osa.client.JsonCaller;
import com.osa.client.SecureJsonCaller;
import com.osa.client.SecureXmlCaller;
import com.osa.client.XmlCaller;
import com.osa.client.ws.SoapClient;
import com.osa.parsers.JsonParser;
import com.osa.parsers.XmlParser;
import com.osa.properties.Method;
import com.osa.properties.TestClass;
import com.osa.properties.TestMethodProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Configuration
@PropertySource("classpath:application-test.properties")
@Import({ ApplicationConfiguration.class })
public class TestConfig {

    @Bean
    public JsonCaller jsonCaller(JsonParser parser, @Value("${endpoint.url.json}") String endpointUrl) {
        return new JsonCaller(parser, endpointUrl);
    }

    @Bean
    public XmlCaller xmlCaller(XmlParser parser, @Value("${endpoint.url.xml}") String endpointUrl) {
        return new XmlCaller(parser, endpointUrl);
    }

    @Bean
    public SecureJsonCaller secureJsonCaller(JsonParser parser,
                                             @Value("${endpoint.url.json.secured}") String endpointUrl,
                                             @Value("${endpoint.url.auth}") String authEndpoint) {
        return new SecureJsonCaller(parser, endpointUrl, authEndpoint);
    }

    @Bean
    public SecureXmlCaller secureXmlCaller(XmlParser parser,
                                           @Value("${endpoint.url.xml.secured}") String endpointUrl,
                                           @Value("${endpoint.url.auth}") String authEndpoint) {
        return new SecureXmlCaller(parser, endpointUrl, authEndpoint);
    }

    @SuppressWarnings("Duplicates")
    @Bean
    public Wss4jSecurityInterceptor clientSecurityInterceptor(@Value("${user.username}") String username,
                                                              @Value("${user.password}") String password) {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setSecurementActions("Timestamp UsernameToken");
        securityInterceptor.setValidateResponse(false);
        securityInterceptor.setSecurementUsername(username);
        securityInterceptor.setSecurementPassword(password);
        return securityInterceptor;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.osa.model");
        return marshaller;
    }

    @Bean("soapClient")
    public SoapClient soapClient(@Value("${endpoint.url.soap}") String endpointUrl,
                                 Jaxb2Marshaller marshaller) {
        return new SoapClient(endpointUrl, marshaller);
    }

    @Bean("securedSoapClient")
    public SoapClient securedSoapClient(@Value("${endpoint.url.soap.secured}") String endpointUrl,
                                        Jaxb2Marshaller marshaller,
                                        @Qualifier("clientSecurityInterceptor") Wss4jSecurityInterceptor securityInterceptor) {
        return new SoapClient(endpointUrl, marshaller, securityInterceptor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public Map<TestClass, Map<Method, TestMethodProperties>> testProperties(@Value("${charset}") String charset) throws URISyntaxException, IOException {
        URI uri = getClass().getResource("test-parameters.json").toURI();
        String jsonContent = IOUtils.toString(uri, charset);
        Type type = new TypeToken<Map<TestClass, Map<Method, TestMethodProperties>>>() {
        }.getType();

        return new Gson().fromJson(jsonContent, type);
    }

}
