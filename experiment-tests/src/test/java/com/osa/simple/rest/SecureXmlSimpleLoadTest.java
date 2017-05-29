package com.osa.simple.rest;

import com.osa.TestConfig;
import com.osa.client.SecureXmlCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureXmlSimpleLoadTest extends RestSimpleLoadTest {

    @Autowired
    SecureXmlSimpleLoadTest(final SecureXmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
