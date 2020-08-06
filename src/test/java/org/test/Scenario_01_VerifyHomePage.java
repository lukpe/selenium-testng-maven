package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

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
    public void validateTitle() {
        assertEquals(driver.getTitle(), "My Store");
    }

    @Test
    public void validateLogo() {
        hp.checkLogo();
    }

    @Test
    public void validateSearchBar() {
        hp.checkSearchBar();
    }

    @Test
    public void validateShoppingCart() {
        hp.checkShoppingCart();
    }

    @Test
    public void validateSignInLink() {
        hp.checkSignIn();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
