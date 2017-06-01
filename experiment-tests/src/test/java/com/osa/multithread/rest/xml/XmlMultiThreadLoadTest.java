package com.osa.multithread.rest.xml;

import com.osa.TestConfig;
import com.osa.client.XmlCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestExperiment;
import com.osa.multithread.rest.RestMultithreadLoadTest;
import com.osa.properties.Method;
import com.osa.properties.TestClass;
import com.osa.properties.TestMethodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.Map;

import static com.osa.properties.TestClass.multithread;

@RestExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class XmlMultiThreadLoadTest extends RestMultithreadLoadTest {

    @Autowired
    XmlMultiThreadLoadTest(final XmlCaller serviceCaller, Map<TestClass, Map<Method, TestMethodProperties>> testProperties) {
        super(serviceCaller, testProperties.get(multithread));
    }
}
