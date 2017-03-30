package com.osa.model;

import org.apache.commons.lang3.RandomUtils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType
public enum Status {
    available, unavailable, partially;

    public static Status getRandom() {
        int index = RandomUtils.nextInt(0, values().length);
        return values()[index];
    }
}


