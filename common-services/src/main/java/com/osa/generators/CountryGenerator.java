package com.osa.generators;

import com.osa.model.Country;
import com.osa.util.Countries;
import org.springframework.stereotype.Component;

@Component
public class CountryGenerator {

    public Country generateCountry() {
        Countries randomCountry = Countries.getRandomCountry();

        return Country.builder()
                .code(randomCountry.getCode())
                .name(randomCountry.getName())
                .build();
    }
}
