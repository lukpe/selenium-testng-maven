package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class Scenario_02_CreateAccount extends Base {
    WebDriver driver;
    WebDriverWait wait;
    HomePage hp;
    LoginPage lp;
    MyAccountPage map;
    ExcelDriver excel;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        hp = new HomePage(driver);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver, wait);
        excel = new ExcelDriver();
    }

    @Test(priority = 1)
    public void createAccount() {
        //Create an account
        hp.signIn();
        lp.createAccount();
        assertTrue(map.verifyPageHeader());
    }

    @Test(priority = 2)
    public void verifyAccount() {
        //Verify personal information
        hp.signOut();
        lp.signIn();
        assertTrue(map.verifyPageHeader());
        assertTrue(map.verifyPersonalInformation());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
