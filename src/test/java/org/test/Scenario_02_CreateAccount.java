package org.test;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CommonElements;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.testng.Assert.assertTrue;

public class Scenario_02_CreateAccount extends TestBase {
    WebDriver driver;
    WebDriverWait wait;
    CommonElements ce;
    HomePage hp;
    LoginPage lp;
    MyAccountPage map;
    ExcelDriver excel;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        ce = new CommonElements(driver, wait);
        hp = new HomePage(driver, wait);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver);
        excel = new ExcelDriver();
        excel.addNewRow();
    }

    @BeforeMethod
    public void setThreadName(Method method) {
        ThreadContext.put("threadName", method.getName());
    }

    @Test(groups = {"accountCreation"})
    public void createAccount() {
        //Create an account
        hp.signIn();
        lp.createAccount();
        ce.waitForHeading("MY ACCOUNT");
    }

    @Test(groups = "accountCreation", dependsOnMethods = "createAccount")
    public void verifyAccount() {
        //Verify personal information
        hp.signOut();
        lp.signIn();
        ce.waitForHeading("MY ACCOUNT");
        assertTrue(map.verifyPersonalInformation());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
