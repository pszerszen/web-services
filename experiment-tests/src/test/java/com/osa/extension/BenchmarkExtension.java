package com.osa.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExtensionContext;

import java.util.Collections;
import java.util.Map;

public class BenchmarkExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(final TestExtensionContext context) throws Exception {
        long elapsed = System.currentTimeMillis() - getStore(context).remove(context.getTestMethod().get(), long.class);
        String message = String.format("Test %s took %d ms.%n", context.getTestMethod().get().getName(), elapsed);
        context.publishReportEntry(
                createMapWithPair("Benchmark", message));
    }

    @Override
    public void beforeTestExecution(final TestExtensionContext context) throws Exception {
        getStore(context).put(context.getTestMethod().get(), System.currentTimeMillis());
    }

    private Map<String, String> createMapWithPair(String key, String message) {
        return Collections.singletonMap(key, message);
    }

    private ExtensionContext.Store getStore(TestExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context));
    }
}
