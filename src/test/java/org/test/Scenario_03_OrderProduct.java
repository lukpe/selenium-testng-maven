package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.utils.ExcelDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

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
    OrderPage op;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        hp = new HomePage(driver);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver, wait);
        sp = new SearchPage(driver, wait);
        op = new OrderPage(driver, wait);
        excel = new ExcelDriver();
        actions = new Actions(driver);
    }

    @Parameters({"product"})
    @Test(priority = 1)
    public void addToCart(String product) {
        product = product.toLowerCase();
        hp.searchProduct(product);
        assertTrue(sp.checkSearchResult(product));
        sp.addProductToCart();
        assertTrue(sp.verifyMessageHeader("Product successfully added to your shopping cart"));
        sp.getContinueShopping().click();
        assertTrue(hp.checkCartQuantity("1"));
    }

    @Parameters({"product"})
    @Test(priority = 2)
    public void checkOut(String product) {
        product = product.toLowerCase();
        hp.proceedCheckOut();
        assertTrue(op.verifyProductQtyTitle("1 Product"));
        assertTrue(op.verifyProductName(product));
        assertTrue(op.verifyProductQty(1));
        op.addProduct();
        assertTrue(op.verifyProductQty(2));
        assertTrue(op.verifyProductQtyTitle("2 Products"));
        op.removeProduct();
        assertTrue(op.verifyProductQty(1));
        assertTrue(op.verifyProductQtyTitle("1 Product"));
        op.proceedCheckOut();
        //Sign In
        String[] loginData = excel.getLoginData();
        lp.signIn(loginData[0], loginData[1]);
        //Address
        assertTrue(op.verifyTitle("ADDRESSES"));

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
