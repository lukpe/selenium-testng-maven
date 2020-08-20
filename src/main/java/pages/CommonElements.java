package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonElements {
    private final WebDriverWait wait;

    public CommonElements(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "page-heading")
    WebElement pageHeading;

    @FindBy(className = "page-subheading")
    WebElement subheading;

    public String getHeading() {
        return pageHeading.getText();
    }

    public String getSubheading() {
        return subheading.getText();
    }

    public void waitForHeaderChange(String previous) {
        wait.until(driver1 -> !pageHeading.getText().contains(previous));
    }
}
