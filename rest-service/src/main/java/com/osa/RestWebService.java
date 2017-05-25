package com.osa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:common-services.properties")
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackageClasses = RestWebService.class,
        exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class RestWebService {

    public static void main(String... args) throws Exception {
        SpringApplication.run(RestWebService.class, args);
    }
}
