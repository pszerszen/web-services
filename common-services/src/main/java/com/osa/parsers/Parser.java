package com.osa.parsers;

import org.apache.commons.lang3.tuple.Pair;

public interface Parser {

    <T> String parseToContent(T t, Pair<String, String>... headers);

    <T> T parseFromContent(String content, Class<T> type);
}
