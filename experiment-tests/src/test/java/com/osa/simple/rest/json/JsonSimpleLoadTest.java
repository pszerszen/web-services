package com.osa.simple.rest.json;

import com.osa.TestConfig;
import com.osa.client.JsonCaller;
import com.osa.simple.rest.RestSimpleLoadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
public class JsonSimpleLoadTest extends RestSimpleLoadTest {

    @Autowired
    JsonSimpleLoadTest(final JsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
