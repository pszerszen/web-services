package com.osa.multithread.rest.xml;

import com.osa.TestConfig;
import com.osa.client.SecureXmlCaller;
import com.osa.extension.Benchmark;
import com.osa.multithread.rest.RestMultithreadLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureXmlMultiThreadLoadTest extends RestMultithreadLoadTest {

    @Autowired
    SecureXmlMultiThreadLoadTest(final SecureXmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
