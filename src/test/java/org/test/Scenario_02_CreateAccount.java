package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.utils.ExcelDriver;
import pages.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyAccountPage;

import java.io.IOException;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Scenario_02_CreateAccount extends Base {
    WebDriver driver;
    WebDriverWait wait;
    HomePage hp;
    LoginPage lp;
    MyAccountPage map;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        driver.get(properties.getProperty("homePageURL"));
        hp = new HomePage(driver);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver, wait);
    }

    @Test
    public void createAccount() {
        //Generate Login data
        String firstName = "John";
        String lastName = "Kowalski";
        int rndNum = randomNumber();
        String randomEmail = "test" + rndNum + "@test.test";
        String randomPassword = "Test" + rndNum + "!";
        //Write login data to excel sheet
        ExcelDriver excel = new ExcelDriver();
        excel.writeLogin(randomEmail, randomPassword);
        //Create an account
        hp.signIn();
        lp.createAccount(firstName, lastName, randomEmail, randomPassword);
        //Verify created account
        assertTrue(map.verifyPageHeader());
        hp.signOut();
        lp.signIn(randomEmail, randomPassword);
        assertTrue(map.verifyPageHeader());
        assertTrue(map.verifyPersonalInformation(firstName, lastName, randomEmail));
    }

    private int randomNumber() {
        Random rnd = new Random();
        int upperBound = 10000;
        return rnd.nextInt(upperBound);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
