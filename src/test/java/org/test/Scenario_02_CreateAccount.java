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

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class Scenario_02_CreateAccount extends TestBase {
    WebDriver driver;
    WebDriverWait wait;
    CommonElements ce;
    HomePage hp;
    LoginPage lp;
    MyAccountPage map;
    ExcelDriver excel;

    @BeforeClass
    public void setUp() {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        ce = new CommonElements(driver);
        hp = new HomePage(driver);
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
        assertEquals(ce.getHeading(), "MY ACCOUNT");
    }

    @Test(groups = "accountCreation", dependsOnMethods = "createAccount")
    public void verifyAccount() {
        //Verify personal information
        hp.signOut();
        lp.signIn();
        assertEquals(ce.getHeading(), "MY ACCOUNT");
        map.getPersonalInformation();
        assertEquals(ce.getSubheading(), "YOUR PERSONAL INFORMATION");
        assertEquals(map.getFirstName(), excel.getColumnValue("firstname"));
        assertEquals(map.getLastName(), excel.getColumnValue("lastname"));
        assertEquals(map.getEmail(), excel.getColumnValue("email"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
