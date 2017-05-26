package com.osa;

import com.osa.config.RestWebServiceConfig;
import com.osa.security.OAuth2Config;
import com.osa.security.WebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({OAuth2Config.class, WebSecurityConfig.class})
@PropertySource("classpath:application-test.properties")
public class TestConfig extends RestWebServiceConfig {
}
