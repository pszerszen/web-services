package com.osa;

import com.osa.client.ws.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Component
public class AuthenticatedWebServiceCaller extends SoapClient {

    @Autowired
    public AuthenticatedWebServiceCaller(@Value("${user.username}") String username, @Value("${user.password}") String password) {
        super(securityInterceptor(username, password));
    }

    private static ClientInterceptor securityInterceptor(String username, String password) {
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setSecurementActions("Timestamp UsernameToken");
        wss4jSecurityInterceptor.setSecurementUsername(username);
        wss4jSecurityInterceptor.setSecurementPassword(password);
        return wss4jSecurityInterceptor;
    }
}
