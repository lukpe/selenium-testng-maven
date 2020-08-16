package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class Scenario_01_VerifyHomePage extends Base {
    WebDriver driver;
    WebDriverWait wait;
    HomePage hp;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        hp = new HomePage(driver, wait);
    }

    @Test
    public void validatePageTitle() {
        assertTrue(hp.verifyPageTitle("My Store"));
    }

    @Ignore
    @Test
    public void negativePageTitle(){
        assertTrue(hp.verifyPageTitle("Store"));
    }

    @Test
    public void validateLogo() {
        assertTrue(hp.verifyLogo());
    }

    @Test
    public void validateSearchBar() {
        assertTrue(hp.verifySearchBar());
    }

    @Test
    public void validateShoppingCart() {
        assertTrue(hp.verifyShoppingCart());
    }

    @Test
    public void validateSignInLink() {
        assertTrue(hp.verifySignIn());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
