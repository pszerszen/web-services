package com.osa;

import com.osa.config.RestWebServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-test.properties")
public class TestConfig extends RestWebServiceConfig {
}
