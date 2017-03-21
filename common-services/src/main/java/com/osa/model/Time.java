package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Time implements Serializable {
    private static final long serialVersionUID = 4295105571293533233L;

    private long timestamp;
    private String timezone;
}
