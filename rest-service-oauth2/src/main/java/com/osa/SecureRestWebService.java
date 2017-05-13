package com.osa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@PropertySource({"classpath:common-services.properties","classpath:security.properties"})
@EnableConfigurationProperties
@EnableResourceServer
@SpringBootApplication(scanBasePackageClasses = SecureRestWebService.class,
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SecureRestWebService {

    public static void main(String... args) throws Exception {
        SpringApplication.run(SecureRestWebService.class, args);
    }
}
