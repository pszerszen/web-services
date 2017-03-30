package com.osa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ComponentScan(basePackageClasses = ApplicationConfiguration.class)
@EnableConfigurationProperties
public class ApplicationConfiguration {

    @Bean
    public Map<Class<?>, JAXBContext> contextMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
