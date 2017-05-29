package com.osa.multithread.ws;

import com.osa.TestConfig;
import com.osa.client.ws.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
public class SoapMultithreadLoadTest extends AbstractSoapMultithreadLoadTest {

    @Autowired
    public SoapMultithreadLoadTest(@Qualifier("soapClient") final SoapClient serviceCaller) {
        super(serviceCaller, "Soap");
    }
}
