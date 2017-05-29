package com.osa.multithread;

import com.osa.ResponseWrapperSupplier;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public abstract class MulithreadLoadTest {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private final ResponseWrapperSupplier serviceCall;
    private final String name;
    private final int numberOfCalls;
    private final int numberOfThreads;

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
                .append(String.format("multithreaded-%s-%scalls-%sthreads-%s.csv",
                        name,
                        numberOfCalls,
                        numberOfThreads,
                        System.currentTimeMillis()))
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
    @SneakyThrows
    void multiThredTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        IntStream.range(0, numberOfCalls).forEach(i ->
                executorService.submit(this::callAndSaveMetrics));
        executorService.awaitTermination(30, TimeUnit.MINUTES);
    }

    @SneakyThrows
    private void callAndSaveMetrics() {
        try {
            append(serviceCall.get().toCsvRow());
        } catch (Exception e) {
            log.error("Exception while calling API", e);
            append("0,0,0");
        }
    }

    @SneakyThrows
    private synchronized void append(String line) {
        writer.append(line)
                .append(LINE_SEPARATOR);
    }
}
