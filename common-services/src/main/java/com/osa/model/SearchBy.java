package com.osa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.Objects;

@XmlType(name = "searchBy")
@XmlEnum
public enum SearchBy {

    @XmlEnumValue("cities")
    CITIES("cities"),
    @XmlEnumValue("stations")
    STATIONS("stations"),
    @XmlEnumValue("mixed")
    MIXED("mixed");
    private final String value;

    SearchBy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchBy fromValue(String v) {
        return Arrays.stream(values())
                .filter(searchBy -> Objects.equals(v, searchBy.value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(v));
    }

}
