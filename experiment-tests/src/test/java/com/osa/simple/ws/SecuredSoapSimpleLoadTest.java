package com.osa.simple.ws;

import com.osa.TestConfig;
import com.osa.client.ws.SoapClient;
import com.osa.extension.Benchmark;
import com.osa.extension.SoapSecureExperiment;
import com.osa.properties.Method;
import com.osa.properties.TestClass;
import com.osa.properties.TestMethodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.Map;

import static com.osa.properties.TestClass.simple;

@SoapSecureExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SecuredSoapSimpleLoadTest extends AbstractSoapSimpleLoadTest {

    @Autowired
    public SecuredSoapSimpleLoadTest(@Qualifier("securedSoapClient") final SoapClient serviceCaller, Map<TestClass, Map<Method, TestMethodProperties>> testProperties) {
        super(serviceCaller, "Soap", testProperties.get(simple));
    }
}
