package com.osa.simple;

import com.osa.ResponseWrapperSupplier;
import com.osa.client.ResponseWrapper;
import com.osa.utils.XlsxUtils;
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
import java.util.stream.IntStream;

import static com.osa.Constansts.FILE_SEPARATOR;
import static com.osa.utils.XlsxUtils.insertRow;

@Slf4j
@RequiredArgsConstructor
public abstract class SimpleLoadTest {

    private final ResponseWrapperSupplier serviceCall;
    private final String name;
    private final int numberOfCalls;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String sheetName;

    @BeforeEach
    void setUp() throws IOException {
        sheetName = String.format("simple-%s-%scalls-%s.xlsx", name, numberOfCalls, System.currentTimeMillis());
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
        XlsxUtils.addAverageValuesAndExport(workbook, sheet, numberOfCalls, this::createOutputFile);
    }

    @Test
    void simpleTest() {
        IntStream.rangeClosed(1, numberOfCalls).forEach(this::callAndSaveMetrics);
    }

    @SneakyThrows
    private void callAndSaveMetrics(int i) {
        try {
            ResponseWrapper responseWrapper = serviceCall.get();
            insertRow(sheet, i,
                    responseWrapper.getRequestSize(),
                    responseWrapper.getResponseSize(),
                    responseWrapper.getExecutionTimeInMillis());
        } catch (Exception e) {
            log.error("Exception while calling API", e);
            insertRow(sheet, i, 0L, 0L, 0L);
        } finally {
            log.info("Call nr: {}", i);
        }
    }
}
