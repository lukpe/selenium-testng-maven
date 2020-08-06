package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

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
        hp.waitPageTitle("My Store");
    }

    @Test
    public void validateLogo() {
        hp.waitLogo();
    }

    @Test
    public void validateSearchBar() {
        hp.waitSearchBar();
    }

    @Test
    public void validateShoppingCart() {
        hp.waitShoppingCart();
    }

    @Test
    public void validateSignInLink() {
        hp.waitSignIn();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
