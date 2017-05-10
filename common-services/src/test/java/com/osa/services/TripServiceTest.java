package com.osa.services;

import com.osa.TestConfig;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitJupiterConfig(TestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TripServiceTest {

    private TripRequest request;

    private final TripService tripService;

    @BeforeEach
    void setUp() throws Exception {
        request = mock(TripRequest.class);
        when(request.getDepartureDateAsLocalDateTime())
                .thenReturn(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testSearchTrip() {
        Trip trip = tripService.searchTrip(request, 35);

        assertNotNull(trip);
        assertEquals(35, trip.getItems().size());
    }

}