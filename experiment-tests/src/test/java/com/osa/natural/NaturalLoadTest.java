package com.osa.natural;

import com.osa.ResponseWrapperSupplier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.osa.Constansts.FILE_SEPARATOR;
import static com.osa.Constansts.LINE_SEPARATOR;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@RequiredArgsConstructor
public abstract class NaturalLoadTest {
    private final String name;

    @Value("${natural.calls}")
    private int numberOfCalls;
    @Value("${natural.threads}")
    private int numberOfThreads;

    private BufferedWriter writer;
    private SecureRandom random = new SecureRandom(RandomStringUtils.random(100_000).getBytes());

    protected abstract ResponseWrapperSupplier heartBeat();

    protected abstract ResponseWrapperSupplier network();

    protected abstract ResponseWrapperSupplier origins();

    protected abstract ResponseWrapperSupplier destinations();

    protected abstract ResponseWrapperSupplier trip();

    private ResponseWrapperSupplier randomCall() {
        double rand = random.nextDouble();
        if (rand <= 0.01) {
            return heartBeat();
        } else if (rand <= 0.1) {
            return network();
        } else if (rand <= 0.15) {
            return origins();
        } else if (rand <= 0.3) {
            return destinations();
        } else {
            return trip();
        }
    }

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
                .append(String.format("natural-%s-%scalls-%sthreads-%s.csv",
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
    void testNatural() {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        IntStream.rangeClosed(1, numberOfCalls).forEach(i -> executorService.submit(this::callAndSaveMetrics));
        executorService.shutdown();
        executorService.awaitTermination(30, MINUTES);
    }

    @SneakyThrows
    private void callAndSaveMetrics() {
        try {
            append(randomCall().get().toCsvRow());
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
