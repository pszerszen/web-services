package com.osa.simple.rest.xml;

import com.osa.TestConfig;
import com.osa.client.XmlCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestExperiment;
import com.osa.simple.rest.RestSimpleLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@RestExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class XmlSimpleLoadTest extends RestSimpleLoadTest {

    @Autowired
    XmlSimpleLoadTest(final XmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
