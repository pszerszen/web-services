package com.osa;

import com.osa.client.JsonCaller;
import com.osa.client.SecureJsonCaller;
import com.osa.client.SecureXmlCaller;
import com.osa.client.XmlCaller;
import com.osa.client.ws.SoapClient;
import com.osa.parsers.JsonParser;
import com.osa.parsers.XmlParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

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
                                           @Value("${endpoint.url.json.secured}") String endpointUrl,
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
    public SoapClient securedSoapClient(@Value("${endpoint.url.soap}") String endpointUrl,
                                        Jaxb2Marshaller marshaller,
                                        @Qualifier("clientSecurityInterceptor") Wss4jSecurityInterceptor securityInterceptor) {
        return new SoapClient(endpointUrl, marshaller, securityInterceptor);
    }

}
