package com.osa.multithread.rest.json;

import com.osa.TestConfig;
import com.osa.client.SecureJsonCaller;
import com.osa.extension.Benchmark;
import com.osa.multithread.rest.RestMultithreadLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureJsonMultiThreadLoadTest extends RestMultithreadLoadTest {

    @Autowired
    SecureJsonMultiThreadLoadTest(final SecureJsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
