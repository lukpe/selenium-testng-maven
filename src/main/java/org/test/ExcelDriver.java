package org.test;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelDriver {
    File file;
    FileInputStream fis;
    Workbook loginData;
    Sheet sheet;
    private int cellIndex;
    private final String filename = "TestData.xls";
    private final String template = System.getProperty("user.dir") + "\\src\\test\\resources\\" + filename;
    private final String target = System.getProperty("user.dir") + "\\target\\test-data\\";

    public void copyTemplate() {
        File templateFile = new File(template);
        File targetFile = new File(target);
        try {
            FileUtils.copyFileToDirectory(templateFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewRow() {
        try {
            setUp();
            sheet.createRow(sheet.getLastRowNum() + 1);
            fis.close();
            writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setColumnValue(String columnName, String value) {
        try {
            setUp();
            for (int i = 0; i < 2; i++) {
                if (i == 1) {
                    columnName = "write_date";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    value = sdf.format(date);
                }
                try {
                    cellIndex = getColumnByName(columnName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                Row row = sheet.getRow(sheet.getLastRowNum());
                Cell cell = row.createCell(cellIndex);
                cell.setCellValue(value);
            }
            fis.close();
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getColumnValue(String columnName) throws Exception {
        try {
            setUp();
            try {
                cellIndex = getColumnByName(columnName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            Row row = sheet.getRow(sheet.getLastRowNum());
            Cell cell = row.getCell(cellIndex);
            String cellValue = cell.getRichStringCellValue().getString();
            fis.close();
            return cellValue;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new Exception("Invalid column name");
    }

    private void setUp() {
        try {
            file = new File(target + filename);
            fis = new FileInputStream(file);
            loginData = new HSSFWorkbook(fis);
            sheet = loginData.getSheet("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            loginData.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getColumnByName(String columnName) throws Exception {
        Row row = sheet.getRow(0);
        for (Cell cell : row) {
            if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        throw new Exception("Invalid column name");
    }
}
