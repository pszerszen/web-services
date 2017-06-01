package com.osa.natural.ws;

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
public class SoapNaturalLoadTest extends AbstractSoapNaturalLoadTest {

    @Autowired
    SoapNaturalLoadTest(@Qualifier("soapClient") final SoapClient serviceCaller) {
        super(serviceCaller, "Soap");
    }
}
