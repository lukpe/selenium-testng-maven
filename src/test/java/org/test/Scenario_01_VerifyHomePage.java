package org.test;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.HomePage;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.testng.Assert.assertTrue;

public class Scenario_01_VerifyHomePage extends TestBase {
    WebDriver driver;
    WebDriverWait wait;
    HomePage hp;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        hp = new HomePage(driver, wait);
    }

    @BeforeMethod
    public void setThreadName(Method method){
        ThreadContext.put("threadName", method.getName());
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
