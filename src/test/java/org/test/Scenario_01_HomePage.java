package org.test;

import objects.page.HomePage;
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
        assertTrue(hp.getLogo().isDisplayed());
    }

    @Test
    public void validateSearchBar() {
        assertTrue(hp.getSearchBar().isDisplayed());
    }

    @Test
    public void validateShoppingCart() {
        assertTrue(hp.getShoppingCart().isDisplayed());
    }

    @Test
    public void validateSignInLink(){
        assertTrue(hp.getSignInLink().isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
