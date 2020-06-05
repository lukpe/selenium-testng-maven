package org.test.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDriver {
    public void writeLogin(String login, String password) {
        File file = new File(System.getProperty("user.dir") + "\\resources\\LoginData.xls");
        try {
            FileInputStream fis = new FileInputStream(file);
            Workbook loginData = new HSSFWorkbook(fis);
            Sheet sheet = loginData.getSheet("Login");
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(rowCount + 1);
            Cell loginCell = row.createCell(0);
            loginCell.setCellValue(login);
            Cell passwordCell = row.createCell(1);
            passwordCell.setCellValue(password);
            fis.close();
            FileOutputStream fos = new FileOutputStream(file);
            loginData.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
