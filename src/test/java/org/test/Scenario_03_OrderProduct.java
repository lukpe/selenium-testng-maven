package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class Scenario_03_OrderProduct extends TestBase {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    CommonElements ce;
    HomePage hp;
    LoginPage lp;
    MyAccountPage map;
    SearchPage srchp;
    SummaryPage sump;
    AddressPage ap;
    ShippingPage shp;
    PaymentPage pp;
    OrderConfirmationPage ocp;
    private String totalPrice;
    private String totalShipping;

    @BeforeClass
    public void setUp() throws IOException {
        driver = initializeDriver();
        wait = new WebDriverWait(driver, timeOut);
        ce = new CommonElements(driver, wait);
        hp = new HomePage(driver, wait);
        lp = new LoginPage(driver, wait);
        map = new MyAccountPage(driver);
        srchp = new SearchPage(driver, wait);
        sump = new SummaryPage(driver, wait);
        ap = new AddressPage(driver);
        shp = new ShippingPage(driver, wait);
        pp = new PaymentPage(driver, wait);
        ocp = new OrderConfirmationPage(driver, wait);
        actions = new Actions(driver);
    }

    @Parameters({"product", "quantity"})
    @Test()
    public void addToCart(String product, int quantity) {
        product = product.toLowerCase();
        hp.searchProduct(product);
        assertTrue(srchp.checkSearchResult(product));
        srchp.saveProductDetails(product, quantity);
        srchp.addProductToCart();
        assertTrue(srchp.verifyMessageHeader("Product successfully added to your shopping cart"));
        srchp.getContinueShopping().click();
        assertTrue(hp.checkCartQuantity("1"));
        hp.proceedCheckOut();
    }

    @Parameters({"product", "quantity"})
    @Test(dependsOnMethods = "addToCart")
    public void summary(String product, int quantity) {
        product = product.toLowerCase();
        assertTrue(sump.verifyProductQtyTitle(1));
        assertTrue(sump.verifyProductQty(1));
        assertTrue(sump.verifyProductName(product));
        if (quantity > 1) {
            sump.addProduct(quantity);
            assertTrue(sump.verifyProductQtyTitle(quantity));
            assertTrue(sump.verifyProductQty(quantity));
        }
        assertTrue(sump.verifyTotalPrice());
        totalPrice = sump.getTotalPrice();
        totalShipping = sump.getTotalShipping();
        sump.proceedCheckOut();
    }

    @Test(dependsOnGroups = "accountCreation", dependsOnMethods = "summary")
    public void signIn() {
        lp.signIn();
    }

    @Test(dependsOnMethods = "signIn")
    public void addresses() {
        ce.waitForHeading("ADDRESSES");
        assertTrue(ap.verifyAddressData());
        ap.proceedCheckout();
    }

    @Test(dependsOnMethods = "addresses")
    public void shipping() {
        ce.waitForHeading("SHIPPING");
        assertTrue(shp.verifyTotalShipping(totalShipping));
        shp.verifyTermsAndConditions();
        shp.proceedCheckOut();
        assertTrue(shp.verifyErrorMessage("You must agree to the terms of service before continuing."));
        shp.acceptTermsAndConditions();
        shp.proceedCheckOut();
    }

    @Parameters({"product", "payment"})
    @Test(dependsOnMethods = "shipping")
    public void payment(String product, String payment) {
        product = product.toLowerCase();
        ce.waitForHeading("PLEASE CHOOSE YOUR PAYMENT METHOD");
        assertTrue(sump.verifyProductName(product));
        pp.choosePaymentMethod(payment);
        assertTrue(pp.verifyTotalPrice(totalPrice));
        pp.confirmPayment();
        ce.waitForHeading("ORDER CONFIRMATION");
        assertTrue(ocp.verifySuccessMessage(payment, "Your order on My Store is complete."));
        assertTrue(ocp.verifyTotalPrice(totalPrice));
        ocp.saveOrderReference(payment);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
