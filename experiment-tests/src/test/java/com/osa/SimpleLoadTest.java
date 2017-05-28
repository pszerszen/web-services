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
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public abstract class SimpleLoadTest {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    protected final ResponseWrapperSupplier serviceCall;
    private final String name;
    private final int numberOfCalls;

    private BufferedWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        writer = Files.newBufferedWriter(createOutputFile().toPath());
        writer.append("requestSize,responseSize,executionTime")
                .append(LINE_SEPARATOR);
    }

    @SneakyThrows(IOException.class)
    private File createOutputFile() {
        String currentFilename = new StringBuilder(System.getProperty("user.home"))
                .append(System.getProperty("file.separator"))
                .append("web-services")
                .append(System.getProperty("file.separator"))
                .append(String.format("simple-%s-%scalls-%s.csv", name, numberOfCalls, System.currentTimeMillis()))
                .toString();
        File file = new File(currentFilename);
        FileUtils.touch(file);

        return file;
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
