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
    private final String firstName = "John";
    private final String lastName = "Kowalski";
    private String randomEmail;
    private String randomPassword;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        driver.get(properties.getProperty("homePageURL"));
        hp = new HomePage(driver);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver, wait);
        generateLoginData();
    }

    @Test(priority = 1)
    public void createAccount() {
        //Create an account
        hp.signIn();
        lp.createAccount(firstName, lastName, randomEmail, randomPassword);
        assertTrue(map.verifyPageHeader());
    }

    @Test(priority = 2)
    public void verifyAccount() {
        //Verify personal information
        hp.signOut();
        lp.signIn(randomEmail, randomPassword);
        assertTrue(map.verifyPageHeader());
        assertTrue(map.verifyPersonalInformation(firstName, lastName, randomEmail));
    }

    private void generateLoginData(){
        //Generate email and password
        Random rnd = new Random();
        int upperBound = 10000;
        int rndNum = rnd.nextInt(upperBound);
        randomEmail = firstName.toLowerCase() + lastName.toLowerCase() + rndNum + "@selenium.test";
        randomPassword = "Test" + rndNum + "!";
        //Write login data to excel sheet
        ExcelDriver excel = new ExcelDriver();
        excel.writeLoginData(randomEmail, randomPassword);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
