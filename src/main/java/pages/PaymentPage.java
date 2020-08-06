package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage {
    public WebDriver driver;
    private final WebDriverWait wait;
    CommonElements ce;

    public PaymentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        ce = new CommonElements(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "bankwire")
    WebElement bankWire;

    @FindBy(className = "cheque")
    WebElement cheque;

    @FindBy(xpath = "//span[@id='amount']")
    WebElement totalAmount;

    @FindBy(xpath = "//span[contains(text(),'I confirm my order')]")
    WebElement orderConfirmation;

    public boolean choosePaymentMethod(String payment) {
        if (payment.equalsIgnoreCase("bankwire")) {
            return chooseBankWire();
        } else if (payment.equalsIgnoreCase("cheque")) {
            return chooseCheque();
        }
        return false;
    }

    public void confirmPayment() {
        orderConfirmation.click();
    }

    private boolean chooseBankWire() {
        bankWire.click();
        return ce.verifySubHeading("BANK-WIRE PAYMENT.");
    }

    private boolean chooseCheque() {
        cheque.click();
        return ce.verifySubHeading("CHECK PAYMENT");
    }

    public boolean verifyTotalPrice(String correctTotal) {
        wait.until(ExpectedConditions.visibilityOf(totalAmount));
        return totalAmount.getText().trim().contentEquals(correctTotal);
    }

}
