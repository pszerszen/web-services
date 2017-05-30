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
import java.util.stream.IntStream;

import static com.osa.Constansts.FILE_SEPARATOR;
import static com.osa.Constansts.LINE_SEPARATOR;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@RequiredArgsConstructor
public abstract class MulithreadLoadTest {

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
        IntStream.rangeClosed(1, numberOfCalls).forEach(i -> executorService.submit(this::callAndSaveMetrics));
        executorService.shutdown();
        executorService.awaitTermination(30, MINUTES);
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
