package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.ExcelDriver;

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

    @FindBy(xpath = "//span[@class='price']")
    WebElement totalPrice;

    @FindBy(xpath = "//div[@class='box']")
    WebElement confirmationBankWire;

    @FindBy(xpath = "//div[@class='box order-confirmation']")
    WebElement confirmationCheque;

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

    public boolean verifyTotalPrice(String correctTotal) {
        return totalPrice.getText().trim().contentEquals(correctTotal);
    }

    public void saveOrderReference(String payment) {
        String fullText = null;
        if (payment.equalsIgnoreCase("bankwire")) {
            fullText = confirmationBankWire.getText().replace("\n", "");
        } else if (payment.equalsIgnoreCase("cheque")) {
            fullText = confirmationCheque.getText().replace("\n", "");
        }
        assert fullText != null;
        int startIndex = fullText.indexOf("reference") + 10;
        int stopIndex = startIndex + 9;
        String orderReference = fullText.substring(startIndex, stopIndex);
        ExcelDriver excel = new ExcelDriver();
        excel.setColumnValue("order_reference", orderReference);
    }

}
