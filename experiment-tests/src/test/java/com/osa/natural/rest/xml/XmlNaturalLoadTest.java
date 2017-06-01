package com.osa.natural.rest.xml;

import com.osa.TestConfig;
import com.osa.client.XmlCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestExperiment;
import com.osa.natural.rest.RestNaturalLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@RestExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class XmlNaturalLoadTest extends RestNaturalLoadTest {

    @Autowired
    XmlNaturalLoadTest(final XmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
