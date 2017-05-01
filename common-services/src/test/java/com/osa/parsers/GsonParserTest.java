package com.osa.parsers;

import com.google.gson.Gson;
import com.osa.TestConfig;
import com.osa.services.NetworkService;
import com.osa.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

@SpringJUnitJupiterConfig(TestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GsonParserTest {

    private final NetworkService networkService;
    private final TripService tripService;
    private final Gson parser;
}
