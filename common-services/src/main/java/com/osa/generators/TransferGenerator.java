package com.osa.generators;

import com.osa.model.Time;
import com.osa.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferGenerator {

    private final TimeGenerator timeGenerator;

    private final StationGenerator stationGenerator;

    public Transfer generateTransfer(Time from, Time to) {
        return Transfer.builder()
                .departure(from)
                .arrival(to)
                .station(stationGenerator.generateStation())
                .build();
    }
}
