package org.test.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDriver {
    File file;
    FileInputStream fis;
    Workbook loginData;
    Sheet sheet;
    int rowCount;

    public void writeLoginData(String login, String password) {
        setUp();
        try {
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

    public String[] getLoginData(){
        String[] loginData = new String[2];
        try {
            setUp();
            Row row = sheet.getRow(rowCount);
            Cell loginCell = row.getCell(0);
            loginData[0] = loginCell.getStringCellValue();
            Cell passwordCell = row.getCell(1);
            loginData[1] = passwordCell.getStringCellValue();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginData;
    }

    public void setValueByColumnName(String columnName, String value){
        try{
            setUp();
            int cellIndex = getColumnByName(columnName);
            Row row = sheet.getRow(rowCount);
            Cell cell = row.createCell(cellIndex);
            cell.setCellValue(value);
            fis.close();
            FileOutputStream fos = new FileOutputStream(file);
            loginData.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValueByColumnName(String columnName){
        try{
            setUp();
            int cellIndex = getColumnByName(columnName);
            Row row = sheet.getRow(rowCount);
            Cell cell = row.getCell(cellIndex);
            String cellValue = cell.getRichStringCellValue().getString();
            fis.close();
            return cellValue;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Invalid column name";
    }



    private void setUp(){
        try {
            file = new File(System.getProperty("user.dir") + "\\resources\\LoginData.xls");
            fis = new FileInputStream(file);
            loginData = new HSSFWorkbook(fis);
            sheet = loginData.getSheet("Login");
            rowCount = sheet.getLastRowNum();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getColumnByName(String columnName){
        Row row = sheet.getRow(0);
        for(Cell cell : row){
            if(cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(columnName)){
                return cell.getColumnIndex();
            }
        }
        return -1;
    }
}
