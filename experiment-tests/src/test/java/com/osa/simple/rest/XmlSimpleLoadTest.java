package com.osa.simple.rest;

import com.osa.TestConfig;
import com.osa.client.XmlCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
public class XmlSimpleLoadTest extends RestSimpleLoadTest {

    @Autowired
    XmlSimpleLoadTest(final XmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
