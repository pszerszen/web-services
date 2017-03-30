package com.osa.generators;

import com.osa.model.Time;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TimeGenerator {

    private static final String PREFIX = "Europe";

    private static final List<String> AVAILABLE_ZONE_IDS = ZoneId.getAvailableZoneIds().stream()
            .filter(zone -> StringUtils.startsWith(zone, PREFIX))
            .collect(Collectors.toList());

    private static final List<String> AVAILABLE_TIME_ZONES = Stream.of(TimeZone.getAvailableIDs())
            .filter(zone -> StringUtils.startsWith(zone, "Europe"))
            .collect(Collectors.toList());

    public Time generateFor(LocalDateTime localDateTime) {
        ZoneId zoneId = getRandomZoneId();

        long timestamp = localDateTime.atZone(zoneId).toEpochSecond();
        String timezone = AVAILABLE_TIME_ZONES.contains(zoneId.toString()) ?
                zoneId.toString() :
                getRandomTimeZone();

        return Time.builder()
                .timestamp(timestamp)
                .timezone(timezone)
                .build();
    }

    public Time generateBetween(LocalDateTime from, LocalDateTime to) {
        ZoneId zoneId = getRandomZoneId();

        long fromMilliseconds = from.atZone(zoneId).toEpochSecond();
        long toMilliseconds = to.atZone(zoneId).toEpochSecond();

        long timestamp = RandomUtils.nextLong(fromMilliseconds, toMilliseconds);
        String timezone = AVAILABLE_TIME_ZONES.contains(zoneId.toString()) ?
                zoneId.toString() :
                getRandomTimeZone();

        return Time.builder()
                .timestamp(timestamp)
                .timezone(timezone)
                .build();
    }

    private ZoneId getRandomZoneId() {
        int index = RandomUtils.nextInt(0, AVAILABLE_ZONE_IDS.size());
        String id = AVAILABLE_ZONE_IDS.get(index);
        return ZoneId.of(id);
    }

    private String getRandomTimeZone() {
        int index = RandomUtils.nextInt(0, AVAILABLE_TIME_ZONES.size());
        return AVAILABLE_TIME_ZONES.get(index);
    }
}
