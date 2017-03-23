package com.osa.generators;

import com.osa.model.Time;
import com.osa.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferGenerator {

    private final TimeGenerator timeGenerator;

    private final StationGenerator stationGenerator;

    public Transfer generateTransfer(LocalDateTime from, LocalDateTime to) {
        List<Time> times = IntStream.range(0, 2).boxed()
                .map(i -> timeGenerator.generateBetween(from, to))
                .sorted()
                .collect(Collectors.toList());

        return Transfer.builder()
                .departure(times.get(0))
                .arrival(times.get(1))
                .station(stationGenerator.generateStation())
                .build();
    }
}
