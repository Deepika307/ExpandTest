package com.expandtesting.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtils {

    private ExcelUtils() {
    }

    public static Object[][] getSheetData(String workbookPath, String sheetName) {
        Path path = Paths.get(workbookPath);
        if (!Files.exists(path)) {
            throw new IllegalStateException("Excel data file not found at path: " + path.toAbsolutePath());
        }

        DataFormatter formatter = new DataFormatter();
        List<Object[]> rows = new ArrayList<>();

        try (InputStream inputStream = Files.newInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found in workbook: " + sheetName);
            }

            int lastRowNumber = sheet.getLastRowNum();
            if (lastRowNumber < 1) {
                return new Object[0][0];
            }

            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();

            for (int rowIndex = 1; rowIndex <= lastRowNumber; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                Object[] values = new Object[columnCount];
                boolean allEmpty = true;
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String value = formatter.formatCellValue(cell).trim();
                    values[columnIndex] = value;
                    if (!value.isEmpty()) {
                        allEmpty = false;
                    }
                }
                if (!allEmpty) {
                    rows.add(values);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read workbook: " + workbookPath, exception);
        }

        return rows.toArray(new Object[0][]);
    }
}
