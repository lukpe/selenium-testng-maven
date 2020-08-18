package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {
    public WebDriver driver;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'My personal information')]")
    WebElement personalInformation;

    @FindBy(id = "firstname")
    WebElement accountFirstName;

    @FindBy(id = "lastname")
    WebElement accountLastName;

    @FindBy(id = "email")
    WebElement accountEmail;

    public void getPersonalInformation() {
        personalInformation.click();
    }

    public String getFirstName() {
        return accountFirstName.getAttribute("value");
    }

    public String getLastName() {
        return accountLastName.getAttribute("value");
    }

    public String getEmail() {
        return accountEmail.getAttribute("value");
    }
}
