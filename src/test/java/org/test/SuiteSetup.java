package org.test;

import org.testng.annotations.BeforeSuite;

public class SuiteSetup {
    @BeforeSuite
    public void setUp(){
        ExcelDriver excel = new ExcelDriver();
        excel.copyTemplate();
    }
}
