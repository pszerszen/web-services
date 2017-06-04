package com.osa.natural;

import com.osa.ResponseWrapperSupplier;
import com.osa.client.ResponseWrapper;
import com.osa.properties.TestMethodProperties;
import com.osa.utils.XlsxUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.osa.Constansts.FILE_SEPARATOR;
import static com.osa.utils.XlsxUtils.emptyRow;
import static com.osa.utils.XlsxUtils.insertRow;

import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@RequiredArgsConstructor
public abstract class NaturalLoadTest {
    private final String name;
    private final TestMethodProperties properties;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String sheetName;
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
        sheetName = String.format("natural-%s-%scalls-%sthreads-%s.xlsx",
                name,
                properties.getCalls(),
                properties.getThreads(),
                System.currentTimeMillis());
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        XlsxUtils.initSheet(sheet);
    }

    @SneakyThrows(IOException.class)
    private File createOutputFile() {
        String currentFilename = new StringBuilder(System.getProperty("user.home"))
                .append(FILE_SEPARATOR)
                .append("web-services")
                .append(FILE_SEPARATOR)
                .append(sheetName)
                .toString();
        File file = new File(currentFilename);
        FileUtils.touch(file);

        return file;
    }

    @AfterEach
    void tearDown() throws IOException {
        XlsxUtils.addAverageValuesAndExport(workbook, sheet, properties.getCalls(), this::createOutputFile);
    }

    @Test
    @SneakyThrows
    void testNatural() {
        ExecutorService executorService = Executors.newFixedThreadPool(properties.getThreads());
        IntStream.rangeClosed(1, properties.getCalls()).forEach(i -> executorService.submit(() -> callAndSaveMetrics(i)));
        executorService.shutdown();
        executorService.awaitTermination(30, MINUTES);
    }

    @SneakyThrows
    private void callAndSaveMetrics(int i) {
        try {
            append(randomCall().get(), i);
        } catch (SocketException e) {
            log.error("Connection issues while calling API", e);
            MINUTES.sleep(1L);
            append(null, i);
        } catch (Exception e) {
            log.error("Exception while calling API", e);
            append(null, i);
        } finally {
            log.info("Call nr: {}", i);
        }
    }

    @SneakyThrows
    private synchronized void append(final ResponseWrapper responseWrapper, int row) {
        if (responseWrapper == null) {
            emptyRow(sheet, row);
        } else {
            insertRow(sheet, row,
                    responseWrapper.getRequestSize(),
                    responseWrapper.getResponseSize(),
                    responseWrapper.getExecutionTimeInMillis());
        }
    }
}
