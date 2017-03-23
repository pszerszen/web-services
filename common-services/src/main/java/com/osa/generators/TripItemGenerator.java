package com.osa.generators;

import com.osa.model.Link;
import com.osa.model.Status;
import com.osa.model.Time;
import com.osa.model.Transfer;
import com.osa.model.TripItem;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripItemGenerator {

    private final TransferGenerator transferGenerator;

    private final TimeGenerator timeGenerator;

    private final LinkGenerator linkGenerator;

    public TripItem generateTripItem(LocalDateTime from, LocalDateTime to) {
        List<Link> links = IntStream.rangeClosed(1, 3).boxed()
                .map(i -> linkGenerator.generateLink())
                .collect(Collectors.toList());
        return TripItem.builder()
                .id(RandomUtils.nextLong())
                .departure(timeGenerator.generateFor(from))
                .arrival(timeGenerator.generateFor(to))
                .price(BigDecimal.valueOf(RandomUtils.nextDouble(10, 100))
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())
                .transfers(generateTransfers(from, to))
                .status(Status.getRandom())
                .links(links)
                .build();
    }

    private List<Transfer> generateTransfers(final LocalDateTime from, final LocalDateTime to) {
        int transfers = RandomUtils.nextInt(0, 6);

        List<Time> transferTimes = IntStream.range(0, transfers * 2).boxed()
                .map(i -> timeGenerator.generateBetween(from, to))
                .sorted()
                .collect(Collectors.toList());

        List<Transfer> transferList = new ArrayList<>(transfers);
        for (int i = 1; i < transfers; i += 2) {
            transferList.add(transferGenerator.generateTransfer(transferTimes.get(i - 1), transferTimes.get(i)));
        }

        return transferList;
    }
}
