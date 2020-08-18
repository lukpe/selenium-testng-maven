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

    public String getSuccessMessage(String payment) {
        WebElement messageElement;
        try {
            if (payment.equalsIgnoreCase("bankwire")) {
                messageElement = successBankWire;
            } else if (payment.equals("cheque")) {
                messageElement = successCheque;
            } else {
                throw new Exception("Invalid payment method: " + payment);
            }
            wait.until(ExpectedConditions.visibilityOf(messageElement));
            return messageElement.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTotalPrice() {
        return totalPrice.getText().trim();
    }

    public void saveOrderReference(String payment) {
        String fullText = null;
        try {
            if (payment.equalsIgnoreCase("bankwire")) {
                fullText = confirmationBankWire.getText().replace("\n", "");
            } else if (payment.equalsIgnoreCase("cheque")) {
                fullText = confirmationCheque.getText().replace("\n", "");
            } else {
                throw new Exception("Invalid payment method: " + payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert fullText != null;
        int startIndex = fullText.indexOf("reference") + 10;
        int stopIndex = startIndex + 9;
        String orderReference = fullText.substring(startIndex, stopIndex);
        ExcelDriver excel = new ExcelDriver();
        excel.setColumnValue("order_reference", orderReference);
    }

}
