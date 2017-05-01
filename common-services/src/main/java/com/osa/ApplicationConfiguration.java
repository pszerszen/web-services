package com.osa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@ComponentScan(basePackageClasses = ApplicationConfiguration.class)
@EnableConfigurationProperties
public class ApplicationConfiguration {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
    }
}
