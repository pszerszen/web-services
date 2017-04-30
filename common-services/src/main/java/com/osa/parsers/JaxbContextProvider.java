package com.osa.parsers;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JaxbContextProvider {

    private final Map<Class<?>, JAXBContext> context = new ConcurrentHashMap<>();

    @SneakyThrows
    JAXBContext getJaxbContext(Class<?> type) {
        JAXBContext jaxbContext = context.get(type);
        if(jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance(type);
            context.put(type, jaxbContext);
        }
        return jaxbContext;
    }
}
