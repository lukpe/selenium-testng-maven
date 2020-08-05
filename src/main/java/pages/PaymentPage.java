package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage {
    public WebDriver driver;
    CommonElements ce;

    public PaymentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        ce = new CommonElements(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "bankwire")
    WebElement bankWire;

    @FindBy(className = "cheque")
    WebElement cheque;

    @FindBy(xpath = "//span[contains(text(),'I confirm my order')]")
    WebElement orderConfirmation;

    public boolean choosePaymentMethod(String payment) {
        try {
            if (payment.equalsIgnoreCase("bankwire")) {
                return chooseBankWire();
            } else if (payment.equalsIgnoreCase("cheque")) {
                return chooseCheque();
            }
        } finally {
            orderConfirmation.click();
        }
        return false;
    }

    private boolean chooseBankWire() {
        bankWire.click();
        return ce.verifySubHeading("BANK-WIRE PAYMENT.");
    }

    private boolean chooseCheque() {
        cheque.click();
        return ce.verifySubHeading("CHECK PAYMENT");
    }

}
