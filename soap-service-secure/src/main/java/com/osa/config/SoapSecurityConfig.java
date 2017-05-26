package com.osa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;

import java.util.Properties;

@Configuration
public class SoapSecurityConfig {

    @Bean
    public Properties users(@Value("${user.username}") String username, @Value("${user.password}") String password) {
        Properties properties = new Properties();
        properties.setProperty(username, password);
        return properties;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler(@Qualifier("users") Properties users) {
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        callbackHandler.setUsers(users);
        return callbackHandler;
    }

    @Bean("serverSecurityInterceptor")
    public Wss4jSecurityInterceptor serverSecurityInterceptor(SimplePasswordValidationCallbackHandler securityCallbackHandler) {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationActions("Timestamp UsernameToken");
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler);
        return securityInterceptor;
    }

    @Bean("clientSecurityInterceptor")
    public Wss4jSecurityInterceptor clientSecurityInterceptor(@Value("${user.username}") String username, @Value("${user.password}") String password) {
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setValidationActions("Timestamp NoSecurity");
        wss4jSecurityInterceptor.setSecurementActions("Timestamp UsernameToken");
        wss4jSecurityInterceptor.setSecurementUsername(username);
        wss4jSecurityInterceptor.setSecurementPassword(password);
        return wss4jSecurityInterceptor;
    }
}
