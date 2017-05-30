package com.osa.multithread.rest.xml;

import com.osa.TestConfig;
import com.osa.client.XmlCaller;
import com.osa.extension.Benchmark;
import com.osa.multithread.rest.RestMultithreadLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@Benchmark
@SpringJUnitJupiterConfig(TestConfig.class)
public class XmlMultiThreadLoadTest extends RestMultithreadLoadTest {

    @Autowired
    XmlMultiThreadLoadTest(final XmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
