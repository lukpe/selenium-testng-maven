package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderConfirmationPage {
    public WebDriver driver;
    private final WebDriverWait wait;

    public OrderConfirmationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "cheque-indent")
    WebElement successBankWire;

    @FindBy(xpath = "//p[@class='alert alert-success']")
    WebElement successCheque;

    public boolean verifySuccessMessage(String payment, String message) {
        WebElement messageElement;
        if (payment.equalsIgnoreCase("bankwire")) {
            messageElement = successBankWire;
        } else if (payment.equals("cheque")) {
            messageElement = successCheque;
        } else {
            return false;
        }
        wait.until(ExpectedConditions.visibilityOf(messageElement));
        return messageElement.getText().contains(message);
    }


}
