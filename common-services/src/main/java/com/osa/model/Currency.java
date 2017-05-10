package com.osa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType
public enum Currency {
    EUR, USD, GBP, CHF, PLN, CZK, SEK, CNY, RUB;
}
