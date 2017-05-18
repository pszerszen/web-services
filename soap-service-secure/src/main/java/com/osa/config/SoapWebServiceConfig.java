package com.osa.config;

import com.osa.services.ResponseTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

@SuppressWarnings("Duplicates")
@EnableWs
@Configuration
public class SoapWebServiceConfig extends WsConfigurerAdapter {

    private final ResponseTimeInterceptor responseTimeInterceptor;
    private final Wss4jSecurityInterceptor securityInterceptor;

    @Autowired
    public SoapWebServiceConfig(final ResponseTimeInterceptor responseTimeInterceptor, final Wss4jSecurityInterceptor securityInterceptor) {
        this.responseTimeInterceptor = responseTimeInterceptor;
        this.securityInterceptor = securityInterceptor;
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/soap/*");
    }

    @Bean
    public XsdSchema tripsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("trips.xsd"));
    }

    @Bean(name = "trips")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema, @Value("${namespace}") String namespace) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TripsPort");
        wsdl11Definition.setLocationUri("/soap");
        wsdl11Definition.setTargetNamespace(namespace);
        wsdl11Definition.setSchema(schema);
        return wsdl11Definition;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        super.addInterceptors(interceptors);
        interceptors.add(responseTimeInterceptor);
        interceptors.add(securityInterceptor);
    }
}
