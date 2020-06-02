package org.test;

import objects.page.HomePage;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.testng.annotations.*;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
