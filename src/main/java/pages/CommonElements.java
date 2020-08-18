package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CommonElements {
    public WebDriver driver;

    public CommonElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "page-heading")
    WebElement pageHeading;

    @FindBy(className = "page-subheading")
    WebElement subheading;

    public String getHeading(){
        return pageHeading.getText();
    }

    public String getSubheading(){
        return subheading.getText();
    }
}
