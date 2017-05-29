package com.osa.natural.rest.xml;

import com.osa.TestConfig;
import com.osa.client.SecureXmlCaller;
import com.osa.natural.rest.RestNaturalLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureXmlNaturalLoadTest extends RestNaturalLoadTest {

    @Autowired
    SecureXmlNaturalLoadTest(final SecureXmlCaller serviceCaller) {
        super(serviceCaller);
    }
}
