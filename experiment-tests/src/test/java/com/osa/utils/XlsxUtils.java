package com.osa.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
public final class XlsxUtils {

    public static void initSheet(final XSSFSheet sheet) {
        insertRow(sheet, 0,
                "Rozmiar zapytania [B]",
                "Rozmiar odpowiedzi [B]",
                "Czas obsłużenia [ms]",
                "Czas obsłużenia [s]",
                "Średni rozmiar zapytania [B]",
                "Średni rozmiar odpowiedzi [B]",
                "Średni czas obsłużenia [ms]",
                "",
                "Błędy");
    }

    public static void emptyRow(XSSFSheet sheet, int row) {
        insertRow(sheet, row, EMPTY, EMPTY, EMPTY);
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
            XSSFDataFormat format = workbook.createDataFormat();
            XSSFCellStyle byteStyle = workbook.createCellStyle();
            byteStyle.setDataFormat(format.getFormat("### ### ### ### ###"));
            XSSFCellStyle timeStyle = workbook.createCellStyle();
            timeStyle.setDataFormat(format.getFormat("### ### ### ### ###.##"));

            int endrow = numberOfCalls + 1;

            XSSFRow row = sheet.getRow(1);
            XSSFCell cell = row.createCell(4);
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("AVERAGE(A2:A" + endrow + ")");
            cell.setCellStyle(byteStyle);

            cell = row.createCell(5);
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("AVERAGE(B2:B" + endrow + ")");
            cell.setCellStyle(byteStyle);

            cell = row.createCell(6);
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("AVERAGE(C2:C" + endrow + ")");
            cell.setCellStyle(timeStyle);

            cell = row.createCell(8);
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("COUNTIF(A2:A" + endrow + ",\"\")");
            cell.setCellStyle(byteStyle);

            IntStream.rangeClosed(1, endrow).forEach(rowNr ->
                    Optional.ofNullable(sheet.getRow(rowNr))
                            .map(cells -> cells.createCell(3))
                            .ifPresent(rowCell -> {
                                rowCell.setCellType(CellType.FORMULA);
                                rowCell.setCellFormula(String.format("C%s/1000", rowNr + 1));
                            }));

            IntStream.range(0, 8).forEach(sheet::autoSizeColumn);
        } catch (Exception e) {
            log.error("Incident", e);
        }

        try (FileOutputStream outputStream = new FileOutputStream(file.get())) {
            workbook.write(outputStream);
        }
    }
}
