package com.osa.multithread.rest.json;

import com.osa.TestConfig;
import com.osa.client.JsonCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestExperiment;
import com.osa.multithread.rest.RestMultithreadLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@RestExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class JsonMultiThreadLoadTest extends RestMultithreadLoadTest {

    @Autowired
    JsonMultiThreadLoadTest(final JsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
