package com.osa.generators;

import com.osa.model.Link;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class LinkGenerator {

    public Link generateLink() {
        return Link.builder()
                .reference(RandomStringUtils.randomAlphabetic(7))
                .url(RandomStringUtils.randomAlphanumeric(35))
                .build();
    }
}
