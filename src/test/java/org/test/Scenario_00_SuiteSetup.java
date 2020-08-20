package org.test;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Scenario_00_SuiteSetup {
    @BeforeSuite
    public void copyExcelTemplate() {
        new ExcelDriver().copyTemplate();
    }

    @AfterSuite
    public void mergeLogFiles() {
        new LogFiles().merge();
    }
}
