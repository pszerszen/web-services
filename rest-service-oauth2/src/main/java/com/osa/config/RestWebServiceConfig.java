package com.osa.config;

import com.osa.ApplicationConfiguration;
import com.osa.services.ResponseTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import(ApplicationConfiguration.class)
public class RestWebServiceConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ResponseTimeInterceptor responseTimeInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(responseTimeInterceptor);
    }
}
