package com.osa.simple.ws;

import com.osa.TestConfig;
import com.osa.client.ws.SoapClient;
import com.osa.extension.Benchmark;
import com.osa.extension.SoapExperiment;
import com.osa.properties.Method;
import com.osa.properties.TestClass;
import com.osa.properties.TestMethodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.Map;

import static com.osa.properties.TestClass.simple;

@SoapExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SoapSimpleLoadTest extends AbstractSoapSimpleLoadTest {

    @Autowired
    public SoapSimpleLoadTest(@Qualifier("soapClient") final SoapClient serviceCaller, Map<TestClass, Map<Method, TestMethodProperties>> testProperties) {
        super(serviceCaller, "Soap", testProperties.get(simple));
    }
}
