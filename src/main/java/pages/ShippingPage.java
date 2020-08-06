package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShippingPage {
    public WebDriver driver;
    private final WebDriverWait wait;

    public ShippingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='delivery_option_price']")
    WebElement totalShipping;

    @FindBy(linkText = "(Read the Terms of Service)")
    WebElement termsAndConditions;

    @FindBy(xpath = "//iframe[@class='fancybox-iframe']")
    WebElement termsAndCondFrame;

    @FindBy(xpath = "//a[@class='fancybox-item fancybox-close']")
    WebElement frameClose;

    @FindBy(className = "fancybox-error")
    WebElement errorMessage;

    @FindBy(id = "cgv")
    WebElement termsAndCondChckBox;

    @FindBy(xpath = "//button//span[contains(text(),'Proceed to checkout')]")
    WebElement checkOut;

    public boolean verifyTotalShipping(String correctTotal) {
        return totalShipping.getText().contentEquals(correctTotal);
    }

    public void verifyTermsAndConditions() {
        CommonElements ce = new CommonElements(driver, wait);
        termsAndConditions.click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(termsAndCondFrame));
        ce.waitForHeading("TERMS AND CONDITIONS OF USE");
        driver.switchTo().defaultContent();
        frameClose.click();
    }

    public void proceedCheckOut() {
        new Actions(driver).pause(500).moveToElement(checkOut).click().perform();
    }

    public boolean verifyErrorMessage(String message) {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        try {
            return errorMessage.getText().contains(message);
        } finally {
            frameClose.click();
        }
    }

    public void acceptTermsAndConditions() {
        new Actions(driver).pause(500).moveToElement(termsAndCondChckBox).click().perform();
    }
}
