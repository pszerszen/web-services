package com.osa;

import com.osa.client.ResponseWrapper;

@FunctionalInterface
public interface ResponseWrapperSupplier<T> {

    ResponseWrapper<T> get() throws Exception;
}
