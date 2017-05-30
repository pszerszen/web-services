package com.osa.client;

import com.osa.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@Tag("integration-test")
@SpringJUnitJupiterConfig(TestConfig.class)
@DisplayName("Calling authenticated json rest API")
class SecureJsonCallerTest extends AbstractAuthenticatedRestCallerTest {

    @Autowired
    SecureJsonCallerTest(final SecureJsonCaller restCaller) {
        super(restCaller);
    }
}
