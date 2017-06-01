package com.osa.simple.ws;

import com.osa.TestConfig;
import com.osa.client.ws.SoapClient;
import com.osa.extension.Benchmark;
import com.osa.extension.SoapExperiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SoapExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SoapSimpleLoadTest extends AbstractSoapSimpleLoadTest {

    @Autowired
    public SoapSimpleLoadTest(@Qualifier("soapClient") final SoapClient serviceCaller) {
        super(serviceCaller, "Soap");
    }
}
