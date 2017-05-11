package com.osa.model;

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

}