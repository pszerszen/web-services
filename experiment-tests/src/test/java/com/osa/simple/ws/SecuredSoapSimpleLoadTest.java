package com.osa.simple.ws;

import com.osa.TestConfig;
import com.osa.client.ws.SoapClient;
import com.osa.extension.Benchmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SecuredSoapSimpleLoadTest extends AbstractSoapSimpleLoadTest {

    @Autowired
    public SecuredSoapSimpleLoadTest(@Qualifier("securedSoapClient") final SoapClient serviceCaller) {
        super(serviceCaller, "SecuredSoap");
    }
}
