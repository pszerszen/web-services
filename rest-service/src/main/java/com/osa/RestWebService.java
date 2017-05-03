package com.osa;

import com.osa.config.RestWebServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties
@Import(RestWebServiceConfig.class)
@SpringBootApplication(scanBasePackageClasses = RestWebService.class)
public class RestWebService {

    public static void main(String... args) throws Exception {
        SpringApplication.run(RestWebService.class, args);
    }
}
