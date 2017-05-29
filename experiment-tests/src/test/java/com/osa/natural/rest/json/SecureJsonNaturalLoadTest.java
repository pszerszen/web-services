package com.osa.natural.rest.json;

import com.osa.TestConfig;
import com.osa.client.SecureJsonCaller;
import com.osa.natural.rest.RestNaturalLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
public class SecureJsonNaturalLoadTest extends RestNaturalLoadTest {

    @Autowired
    SecureJsonNaturalLoadTest(final SecureJsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
