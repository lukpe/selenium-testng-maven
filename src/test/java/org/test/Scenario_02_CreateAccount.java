package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.utils.ExcelDriver;
import pages.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.IOException;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class Scenario_02_CreateAccount extends Base {
    WebDriver driver;
    HomePage hp;
    LoginPage lp;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        driver.get(properties.getProperty("homePageURL"));
        hp = new HomePage(driver);
        lp = new LoginPage(driver);
    }

    @Test
    public void createAccount(){
        //Generate Login data
        Random rnd = new Random();
        int upperBound = 10000;
        int randomNum = rnd.nextInt(upperBound);
        String randomEmail = "test"  + randomNum +"@test.test";
        String randomPassword = "Test" + randomNum + "!";

        //Write login data to excel sheet
        ExcelDriver excel = new ExcelDriver();
        excel.writeLogin(randomEmail,randomPassword);

        //Fill personal data
        hp.goSignIn();
        lp.typeEmail(randomEmail);
        lp.submitEmail();
        assertTrue(lp.checkEmail(randomEmail));
        lp.fillPersonalInformation(randomPassword);
    }

    @AfterClass
    public void tearDown() {
        //driver.quit();
    }

}
