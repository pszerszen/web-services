package com.osa.parsers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class XmlParser implements Parser {

    @Value("${charset}")
    private String encoding;

    private final JaxbContextProvider contextProvider;

    @Override
    @SneakyThrows
    public <T> String parseToContent(final T t, Pair<String, String>... headers) {
        Marshaller marshaller = contextProvider.getJaxbContext(t.getClass()).createMarshaller();

        // output pretty printed
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(t, writer);
            return writer.toString();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SneakyThrows
    public <T> T parseFromContent(final String content, final Class<T> type) {
        Unmarshaller unmarshaller = contextProvider.getJaxbContext(type).createUnmarshaller();

        try (StringReader reader = new StringReader(content)) {
            return (T) unmarshaller.unmarshal(reader);
        }
    }
}
