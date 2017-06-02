package com.osa.simple;

import com.osa.ResponseWrapperSupplier;
import com.osa.client.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.IntStream;

import static com.osa.Constansts.FILE_SEPARATOR;
import static com.osa.Constansts.LINE_SEPARATOR;

import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@RequiredArgsConstructor
public abstract class SimpleLoadTest {

    private final ResponseWrapperSupplier serviceCall;
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
                .append(FILE_SEPARATOR)
                .append("web-services")
                .append(FILE_SEPARATOR)
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
        IntStream.rangeClosed(1, numberOfCalls).forEach(this::callAndSaveMetrics);
    }

    @SneakyThrows
    private void callAndSaveMetrics(int i) {
        try {
            ResponseWrapper responseWrapper = serviceCall.get();
            writer.append(responseWrapper.toCsvRow());
        } catch (Exception e) {
            log.error("Exception while calling API", e);
            if (e instanceof HttpHostConnectException) {
                log.debug("Waiting 1 minute before next calls...");
                MINUTES.sleep(1L);
            }
            writer.append("0,0,0");
        } finally {
            writer.append(LINE_SEPARATOR);
            log.info("Call nr: {}", i);
        }
    }
}
