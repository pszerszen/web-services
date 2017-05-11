package com.osa.model;

import org.apache.commons.lang3.RandomUtils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.Objects;

@XmlType(name = "status")
@XmlEnum
public enum Status {

    @XmlEnumValue("available")
    AVAILABLE("available"),
    @XmlEnumValue("unavailable")
    UNAVAILABLE("unavailable"),
    @XmlEnumValue("partially")
    PARTIALLY("partially");
    private final String value;

    Status(String v) {
        value = v;
    }

    public static Status getRandom() {
        int index = RandomUtils.nextInt(0, values().length);
        return values()[ index ];
    }

    public String value() {
        return value;
    }

    public static Status fromValue(String v) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(v, status.value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(v));
    }

}
