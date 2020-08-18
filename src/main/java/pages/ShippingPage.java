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
    WebElement termsAndCondCheckBox;

    @FindBy(xpath = "//button//span[contains(text(),'Proceed to checkout')]")
    WebElement checkOut;

    public String getTotalShipping() {
        return totalShipping.getText();
    }

    public String getTermsAndConditionsHeading() {
        CommonElements ce = new CommonElements(driver);
        termsAndConditions.click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(termsAndCondFrame));
        String heading = ce.getHeading();
        driver.switchTo().defaultContent();
        frameClose.click();
        return heading;
    }

    public void proceedCheckOut() {
        new Actions(driver).pause(500).moveToElement(checkOut).click().perform();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        try {
            return errorMessage.getText();
        } finally {
            frameClose.click();
        }
    }

    public void acceptTermsAndConditions() {
        new Actions(driver).pause(500).moveToElement(termsAndCondCheckBox).click().perform();
    }
}
