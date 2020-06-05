package org.test;

import pages.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Scenario_01_HomePage extends Base {
    HomePage hp;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        driver.get(properties.getProperty("homePageURL"));
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
    public void validateSignInLink(){
        assertTrue(hp.checkSignIn());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
