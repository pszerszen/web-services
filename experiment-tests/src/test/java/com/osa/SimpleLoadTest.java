package com.osa;

import com.osa.client.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public abstract class SimpleLoadTest {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String FILES_DIR = new StringBuilder(System.getProperty("user.home"))
            .append(System.getProperty("file.separator"))
            .append("web-services experiments results")
            .append(System.getProperty("file.separator"))
            .toString();

    protected final ResponseWrapperSupplier serviceCall;
    private final String name;
    private final int numberOfCalls;

    private BufferedWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        FileUtils.touch(new File(FILES_DIR));
        String currentFilename = String.format("simple-%s-%scalls-%s", name, numberOfCalls, System.currentTimeMillis());
        writer = Files.newBufferedWriter(Paths.get(FILES_DIR + currentFilename));
        writer.append("requestSize,responseSize,executionTime")
                .append(LINE_SEPARATOR);
    }

    @AfterEach
    void tearDown() throws IOException {
        writer.close();
    }

    @Test
    void simpleTest() {
        IntStream.range(0, numberOfCalls).forEach(this::callAndSaveMetrics);
    }

    @SneakyThrows
    private void callAndSaveMetrics(int i) {
        try {
            ResponseWrapper responseWrapper = serviceCall.get();
            writer.append(responseWrapper.toCsvRow());
        } catch (Exception e) {
            log.error("Exception while calling API", e);
            writer.append("0,0,0");
        } finally {
            writer.append(LINE_SEPARATOR);
        }
    }
}
