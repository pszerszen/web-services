package com.osa.client;

import com.osa.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@Tag("integration-test")
@SpringJUnitJupiterConfig(TestConfig.class)
@DisplayName("Calling xml rest API")
class XmlCallerTest extends AbstractRestCallerTest {

    @Autowired
    XmlCallerTest(final XmlCaller xmlCaller) {
        super(xmlCaller);
    }
}
