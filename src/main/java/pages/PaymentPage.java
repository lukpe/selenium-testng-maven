package pages;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage {
    private final WebDriverWait wait;
    CommonElements ce;

    public PaymentPage(WebDriver driver, WebDriverWait wait) {
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

    public void choosePaymentMethod(String payment) {
        try {
            if (payment.equalsIgnoreCase("bankwire")) {
                bankWire.click();
            } else if (payment.equalsIgnoreCase("cheque")) {
                cheque.click();
            } else {
                throw new InvalidArgumentException("Invalid payment method: " + payment);
            }
        } catch (InvalidElementStateException e) {
            e.printStackTrace();
        }
    }

    public void confirmPayment() {
        orderConfirmation.click();
    }

    public boolean verifySubheading(String payment) {
        try {
            if (payment.equalsIgnoreCase("bankwire")) {
                return ce.getSubheading().matches("BANK-WIRE PAYMENT.");
            } else if (payment.equalsIgnoreCase("cheque")) {
                return ce.getSubheading().matches("CHECK PAYMENT");
            } else {
                throw new InvalidArgumentException("Invalid payment method: " + payment);
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getTotalPrice() {
        wait.until(ExpectedConditions.visibilityOf(totalAmount));
        return totalAmount.getText().trim();
    }

}
