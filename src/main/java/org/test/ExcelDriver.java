package org.test;

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
    File file;
    FileInputStream fis;
    Workbook loginData;
    Sheet sheet;
    private int rowCount;
    private int cellIndex;

    public void setValueByColumnName(String columnName, String value){
        try{
            setUp();
            try{
                cellIndex = getColumnByName(columnName);
            } catch (Exception e) {
                e.getLocalizedMessage();
                e.getStackTrace();
            }
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

    public String getValueByColumnName(String columnName) throws Exception {
        try{
            setUp();
            try{
                cellIndex = getColumnByName(columnName);
            } catch (Exception e) {
                e.getLocalizedMessage();
                e.getStackTrace();
            }
            Row row = sheet.getRow(rowCount);
            Cell cell = row.getCell(cellIndex);
            String cellValue = cell.getRichStringCellValue().getString();
            fis.close();
            return cellValue;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new Exception("Invalid column name");
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

    private int getColumnByName(String columnName) throws Exception {
        Row row = sheet.getRow(0);
        for(Cell cell : row){
            if(cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(columnName)){
                return cell.getColumnIndex();
            }
        }
        throw new Exception("Invalid column name");
    }
}
