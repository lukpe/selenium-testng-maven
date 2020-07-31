package org.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Scenario_01_VerifyHomePage extends Base {
    HomePage hp;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        hp = new HomePage(driver);
    }

    @Test
    public void validateTitle() {
        assertEquals(driver.getTitle(), "My Store");
    }

    @Test
    public void validateLogo() {
        assertTrue(hp.checkLogo());
    }

    @Test
    public void validateSearchBar() {
        assertTrue(hp.checkSearchBar());
    }

    @Test
    public void validateShoppingCart() {
        assertTrue(hp.checkShoppingCart());
    }

    @Test
    public void validateSignInLink() {
        assertTrue(hp.checkSignIn());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
