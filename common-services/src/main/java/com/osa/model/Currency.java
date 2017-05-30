package com.osa.model;

import org.apache.commons.lang3.RandomUtils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "currency")
@XmlEnum
public enum Currency {

    EUR,
    USD,
    GBP,
    CHF,
    PLN,
    CZK,
    SEK,
    CNY,
    RUB;

    public String value() {
        return name();
    }

    public static Currency fromValue(String v) {
        return valueOf(v);
    }

    public static Currency getRandom() {
        int index = RandomUtils.nextInt(0, values().length);
        return values()[ index ];
    }
}