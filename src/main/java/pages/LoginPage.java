package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LoginPage {
    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='email_create']")
    WebElement emailCreate;

    @FindBy(xpath = "//button[@id='SubmitCreate']")
    WebElement submitCreate;

    @FindBy(id = "id_gender1")
    WebElement gender;

    @FindBy(id = "customer_firstname")
    WebElement firstName;

    @FindBy(id = "customer_lastname")
    WebElement lastName;

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "passwd")
    WebElement passwordField;

    @FindBy(xpath = "//select[@id='years']")
    Select years;

    public void typeEmail(String email) {
        emailCreate.sendKeys(email);
    }

    public void submitEmail() {
        submitCreate.click();
    }

    public Boolean checkEmail(String email){
        String fieldValue =  emailField.getAttribute("value");
        return fieldValue.equalsIgnoreCase(email);
    }

    public void fillPersonalInformation(String password) {
        gender.click();
        firstName.sendKeys("Tester");
        lastName.sendKeys("Testinson");
        passwordField.sendKeys(password);
        List<WebElement> yearList = years.getOptions();
        int yearsSize = yearList.size();
        int randomYear = ThreadLocalRandom.current().nextInt(2,yearsSize);
        years.selectByIndex(randomYear);

    }


}
