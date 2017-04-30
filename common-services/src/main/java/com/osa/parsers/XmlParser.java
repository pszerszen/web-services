package com.osa.parsers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static javax.xml.bind.Marshaller.JAXB_ENCODING;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static javax.xml.soap.SOAPMessage.CHARACTER_SET_ENCODING;
import static javax.xml.soap.SOAPMessage.WRITE_XML_DECLARATION;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class XmlParser {

    @Value("${charset}")
    private String encoding;

    private final JaxbContextProvider contextProvider;

    @SneakyThrows
    public <T> String parseToSoapMessage(T t) {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Marshaller marshaller = contextProvider.getJaxbContext(t.getClass()).createMarshaller();
        marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(JAXB_ENCODING, encoding);
        marshaller.marshal(t, document);

        SOAPMessage message = MessageFactory.newInstance().createMessage();
        message.setProperty(WRITE_XML_DECLARATION, TRUE.toString());
        message.setProperty(CHARACTER_SET_ENCODING, encoding);
        message.getSOAPBody().addDocument(document);

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            message.writeTo(output);
            return output.toString();
        }
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T parseFromSoapMessage(String xmlContent, Class<T> type, Pair<String, String>... headers) {
        MimeHeaders mimeHeaders = new MimeHeaders();
        Optional.ofNullable(headers)
                .ifPresent(pairs -> stream(headers)
                        .collect(toMap(Pair::getKey, Pair::getValue))
                        .forEach(mimeHeaders::addHeader));
        SOAPMessage message = MessageFactory.newInstance().createMessage(mimeHeaders,
                new ByteArrayInputStream(xmlContent.getBytes(Charset.defaultCharset())));

        Unmarshaller unmarshaller = contextProvider.getJaxbContext(type).createUnmarshaller();
        return (T) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
    }
}
