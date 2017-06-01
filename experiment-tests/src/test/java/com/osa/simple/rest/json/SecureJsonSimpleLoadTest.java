package com.osa.simple.rest.json;

import com.osa.TestConfig;
import com.osa.client.SecureJsonCaller;
import com.osa.extension.Benchmark;
import com.osa.extension.RestOAuth2Experiment;
import com.osa.simple.rest.RestSimpleLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@RestOAuth2Experiment
@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureJsonSimpleLoadTest extends RestSimpleLoadTest {

    @Autowired
    SecureJsonSimpleLoadTest(final SecureJsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
