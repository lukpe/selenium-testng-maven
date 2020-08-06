package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonElements {
    public WebDriver driver;
    private final WebDriverWait wait;

    public CommonElements(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "page-heading")
    WebElement pageHeading;

    @FindBy(className = "page-subheading")
    WebElement subHeading;

    public void waitForHeading(String title) {
        wait.until(ExpectedConditions.textToBePresentInElement(pageHeading, title));
    }

    public void waitForSubHeading(String title) {
        wait.until(ExpectedConditions.textToBePresentInElement(subHeading, title));
    }


}
