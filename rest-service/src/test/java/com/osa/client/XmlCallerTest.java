package com.osa.client;

import com.osa.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
@DisplayName("Calling xml rest API")
class XmlCallerTest extends AbstractRestCallerTest {

    @Autowired
    XmlCallerTest(final XmlCaller xmlCaller) {
        super(xmlCaller);
    }
}
