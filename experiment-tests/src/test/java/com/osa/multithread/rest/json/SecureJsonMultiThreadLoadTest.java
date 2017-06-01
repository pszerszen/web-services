package com.osa.multithread.rest.json;

import com.osa.TestConfig;
import com.osa.client.SecureJsonCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestOAuth2Experiment;
import com.osa.multithread.rest.RestMultithreadLoadTest;
import com.osa.properties.Method;
import com.osa.properties.TestClass;
import com.osa.properties.TestMethodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.Map;

import static com.osa.properties.TestClass.multithread;

@RestOAuth2Experiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureJsonMultiThreadLoadTest extends RestMultithreadLoadTest {

    @Autowired
    SecureJsonMultiThreadLoadTest(final SecureJsonCaller serviceCaller, Map<TestClass, Map<Method, TestMethodProperties>> testProperties) {
        super(serviceCaller, testProperties.get(multithread));
    }
}
