package com.osa.natural.rest.json;

import com.osa.TestConfig;
import com.osa.client.JsonCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestExperiment;
import com.osa.natural.rest.RestNaturalLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@RestExperiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class JsonNaturalLoadTest extends RestNaturalLoadTest {

    @Autowired
    JsonNaturalLoadTest(final JsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
