package org.test;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.*;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
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
        ce = new CommonElements(driver);
        hp = new HomePage(driver);
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

    @BeforeMethod
    public void setThreadName(Method method) {
        ThreadContext.put("threadName", method.getName());
    }

    @Parameters({"product", "quantity"})
    @Test()
    public void addToCart(String product, int quantity) {
        product = product.toLowerCase();
        hp.searchProduct(product);
        assertTrue(srchp.getSearchResult().contains(product),"Product name missing: " + product);
        srchp.saveProductDetails(product, quantity);
        srchp.addProductToCart();
        assertEquals(srchp.getMessageHeader(), "Product successfully added to your shopping cart");
        srchp.getContinueShopping().click();
        assertEquals(hp.getCartQuantity(), "Cart 1 Product");
        hp.proceedCheckOut();
    }

    @Parameters({"product", "quantity"})
    @Test(dependsOnMethods = "addToCart")
    public void summary(String product, int quantity) {
        product = product.toLowerCase();
        assertTrue(sump.getProductQtyTitle().contains("SHOPPING-CART SUMMARY\nYour shopping cart contains: 1 Product"));
        assertEquals(sump.getProductQty(), "1");
        sump.verifyProductName(product);
        sump.addProduct(quantity);
        assertTrue(sump.getProductQtyTitle().contains("SHOPPING-CART SUMMARY\nYour shopping cart contains: " +
                quantity + " Product"));
        assertEquals(sump.getProductQty(), String.valueOf(quantity));
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
        assertEquals(ce.getHeading(), "ADDRESSES");
        assertTrue(ap.verifyAddressData());
        ap.proceedCheckout();
    }

    @Test(dependsOnMethods = "addresses")
    public void shipping() {
        assertEquals(ce.getHeading(), "SHIPPING");
        assertEquals(shp.getTotalShipping(), totalShipping);
        assertEquals(shp.getTermsAndConditionsHeading(), "TERMS AND CONDITIONS OF USE");
        shp.proceedCheckOut();
        assertEquals(shp.getErrorMessage(), "You must agree to the terms of service before continuing.");
        shp.acceptTermsAndConditions();
        shp.proceedCheckOut();
    }

    @Parameters({"product", "payment"})
    @Test(dependsOnMethods = "shipping")
    public void payment(String product, String payment) {
        product = product.toLowerCase();
        assertEquals(ce.getHeading(), "PLEASE CHOOSE YOUR PAYMENT METHOD");
        sump.verifyProductName(product);
        pp.choosePaymentMethod(payment);
        assertTrue(pp.verifySubheading(payment), "Page subheading mismatch");
        assertEquals(pp.getTotalPrice(), totalPrice);
        pp.confirmPayment();
        assertEquals(ce.getHeading(), "ORDER CONFIRMATION");
        assertEquals(ocp.getSuccessMessage(payment), "Your order on My Store is complete.");
        assertEquals(ocp.getTotalPrice(), totalPrice);
        ocp.saveOrderReference(payment);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
