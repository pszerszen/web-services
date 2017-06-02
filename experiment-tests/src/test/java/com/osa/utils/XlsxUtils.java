package com.osa.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public final class XlsxUtils {

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
}
