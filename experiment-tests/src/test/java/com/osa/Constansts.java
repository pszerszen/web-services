package com.osa;

import java.time.format.DateTimeFormatter;

public interface Constansts {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String LINE_SEPARATOR = System.getProperty("line.separator");
    String FILE_SEPARATOR = System.getProperty("file.separator");
}
