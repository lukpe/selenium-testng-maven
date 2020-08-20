package org.test;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.HomePage;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Scenario_01_VerifyHomePage extends TestBase {
    WebDriver driver;
    HomePage hp;

    @BeforeClass
    public void setUp() {
        driver = initializeDriver();
        hp = new HomePage(driver);
    }

    @BeforeMethod
    public void setThreadName(Method method) {
        ThreadContext.put("threadName", method.getName());
    }

    @Test
    public void validatePageTitle() {
        assertEquals(hp.getPageTitle(), "My Store");
    }

    @Ignore
    @Test
    public void negativePageTitle() {
        assertEquals(hp.getPageTitle(), "Store");
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
    public void validateSignInLink() {
        assertTrue(hp.getSignIn().isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
