package com.osa.parsers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JsonParser implements Parser {

    private final Gson gson;

    @Override
    public <T> String parseToContent(final T t, final Pair<String, String>[] headers) {
        return gson.toJson(t);
    }

    @Override
    public <T> T parseFromContent(final String content, final Class<T> type) {
        return gson.fromJson(content, type);
    }
}
