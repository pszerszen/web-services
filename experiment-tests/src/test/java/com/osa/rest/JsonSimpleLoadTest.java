package com.osa.rest;

import com.osa.client.JsonCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig
public class JsonSimpleLoadTest extends RestSimpleLoadTest {

    @Autowired
    JsonSimpleLoadTest(final JsonCaller serviceCaller) {
        super(serviceCaller);
    }
}
