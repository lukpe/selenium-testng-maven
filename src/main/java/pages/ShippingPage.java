package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public boolean verifyTermsAndConditions() {
        CommonElements ce = new CommonElements(driver, wait);
        termsAndConditions.click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(termsAndCondFrame));
        try {
            return ce.verifyHeading("TERMS AND CONDITIONS OF USE");
        } finally {
            driver.switchTo().defaultContent();
            frameClose.click();
        }
    }

    public void proceedCheckOut() {
        checkOut.click();
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
        termsAndCondChckBox.click();
    }


}
