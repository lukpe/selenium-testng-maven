package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.utils.ExcelDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.SearchPage;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class Scenario_03_OrderProduct extends Base {
    WebDriver driver;
    WebDriverWait wait;
    ExcelDriver excel;
    Actions actions;
    HomePage hp;
    LoginPage lp;
    MyAccountPage map;
    SearchPage sp;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        driver.get(properties.getProperty("homePageURL"));
        driver.manage().window().maximize();
        hp = new HomePage(driver);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver, wait);
        sp = new SearchPage(driver, wait);
        excel = new ExcelDriver();
        actions = new Actions(driver);
    }

    @Test(priority = 1)
    public void logIn() {
        String[] loginData = excel.getLoginData();
        hp.signIn();
        lp.signIn(loginData[0], loginData[1]);
        map.goHome();
        assertTrue(hp.checkLogo());
    }

    @Parameters({"product"})
    @Test(priority = 2)
    public void addToCart(String product) {
        product = product.toLowerCase();
        hp.searchProduct(product);
        assertTrue(sp.checkSearchResult(product));
        sp.addProductToCart();
        assertTrue(sp.verifyMessageHeader("Product successfully added to your shopping cart"));
        sp.getContinueShopping().click();
        assertTrue(hp.checkCartQuantity("1"));
    }

    @Test(priority = 3)
    public void checkOut() {
        hp.proceedCheckOut();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
