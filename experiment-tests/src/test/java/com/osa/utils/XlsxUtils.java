package com.osa.utils;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class XlsxUtils {

    public static void initSheet(final XSSFSheet sheet) {
        insertRow(sheet, 0,
                "Rozmiar zapytania [B]",
                "Rozmiar odpowiedzi [B]",
                "Czas obsłużenia [ms]",
                "",
                "Średni rozmiar zapytania [B]",
                "Średni rozmiar odpowiedzi [B]",
                "Średni czas obsłużenia [ms]");
    }

    public static void insertRow(XSSFSheet sheet, int rowNr, Object... values) {
        XSSFRow row = sheet.createRow(rowNr);
        int column = 0;
        for (Object value : values) {
            XSSFCell cell = row.createCell(column++);
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            }
        }
    }

    @SneakyThrows
    public static void addAverageValuesAndExport(XSSFWorkbook workbook, XSSFSheet sheet, int numberOfCalls, Supplier<File> file) {
        try {
            double averageRequestSize = IntStream.rangeClosed(1, numberOfCalls).boxed()
                    .map(sheet::getRow)
                    .map(cells -> cells.getCell(0))
                    .map(XSSFCell::getNumericCellValue)
                    .mapToLong(Double::longValue)
                    .average().getAsDouble();
            double averageResponseSize = IntStream.rangeClosed(1, numberOfCalls).boxed()
                    .map(sheet::getRow)
                    .map(cells -> cells.getCell(1))
                    .map(XSSFCell::getNumericCellValue)
                    .mapToLong(Double::longValue)
                    .average().getAsDouble();
            double averageResponseTime = IntStream.rangeClosed(1, numberOfCalls).boxed()
                    .map(sheet::getRow)
                    .map(cells -> cells.getCell(2))
                    .map(XSSFCell::getNumericCellValue)
                    .mapToLong(Double::longValue)
                    .average().getAsDouble();
            XSSFRow row = sheet.getRow(1);
            row.createCell(4).setCellValue(averageRequestSize);
            row.createCell(5).setCellValue(averageResponseSize);
            row.createCell(6).setCellValue(averageResponseTime);
        } catch (Exception ignore) {
        }

        try (FileOutputStream outputStream = new FileOutputStream(file.get())) {
            workbook.write(outputStream);
        }
    }
}
