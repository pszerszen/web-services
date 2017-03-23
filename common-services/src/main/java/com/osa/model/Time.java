package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class Time implements Serializable, Comparable<Time> {
    private static final long serialVersionUID = 4295105571293533233L;

    private long timestamp;
    private String timezone;

    @Override
    public int compareTo(final Time o) {
        return Objects.compare(timestamp, o.getTimestamp(), Long::compareTo);
    }
}
