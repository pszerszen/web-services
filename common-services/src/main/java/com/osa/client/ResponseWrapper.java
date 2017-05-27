package com.osa.client;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private long executionTimeInMillis;
    private long requestSize;
    private long responseSize;
    private T response;
}
