package com.osa.multithread;

import com.osa.ResponseWrapperSupplier;
import com.osa.client.ResponseWrapper;
import com.osa.properties.TestMethodProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.osa.Constansts.FILE_SEPARATOR;
import static com.osa.utils.XlsxUtils.*;

import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@RequiredArgsConstructor
public abstract class MulithreadLoadTest {

    private final ResponseWrapperSupplier serviceCall;
    private final String name;
    private final TestMethodProperties properties;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String sheetName;
    int counter = 0;

    @BeforeEach
    void setUp() throws IOException {
        sheetName = String.format("multithreaded-%s-%scalls-%sthreads-%s.xlsx",
                name,
                properties.getCalls(),
                properties.getThreads(),
                System.currentTimeMillis());
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        initSheet(sheet);
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
        addAverageValuesAndExport(workbook, sheet, properties.getCalls(), this::createOutputFile);
    }

    @Test
    @SneakyThrows
    void multiThredTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(properties.getThreads());
        IntStream.rangeClosed(1, properties.getCalls()).forEach(i -> executorService.submit(() -> callAndSaveMetrics(i)));
        executorService.shutdown();
        executorService.awaitTermination(30, MINUTES);
    }

    @SneakyThrows
    private void callAndSaveMetrics(int i) {
        ResponseWrapper responseWrapper = ResponseWrapper.empty();
        try {
            responseWrapper = serviceCall.get();
            append(responseWrapper, i);
        } catch (SocketException e) {
            log.error("Connection issues while calling API", e);
            MINUTES.sleep(1L);
            append(null, i);
        } catch (Exception e) {
            log.error("Exception while calling API", e);
            append(null, i);
        } finally {
            log.info("Call nr: {} took {} ms. {}/{} done.", i, responseWrapper.getExecutionTimeInMillis(),
                    counter++, properties.getCalls());
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
