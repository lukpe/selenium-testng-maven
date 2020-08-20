package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {
    private static final String VALUE = "value";

    public MyAccountPage(WebDriver driver) {
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
        return accountFirstName.getAttribute(VALUE);
    }

    public String getLastName() {
        return accountLastName.getAttribute(VALUE);
    }

    public String getEmail() {
        return accountEmail.getAttribute(VALUE);
    }
}
