package com.osa.model;

import org.apache.commons.lang3.RandomUtils;

public enum Status {
    available, unavailable, partially;

    public static Status getRandom() {
        int index = RandomUtils.nextInt(0, values().length);
        return values()[index];
    }
}


