package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LoginPage {
    public WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='email_create']")
    WebElement emailCreate;

    @FindBy(xpath = "//button[@id='SubmitCreate']")
    WebElement submitCreate;

    @FindBy(id = "id_gender1")
    WebElement gender;

    @FindBy(id = "customer_firstname")
    WebElement customerFirstName;

    @FindBy(id = "customer_lastname")
    WebElement customerLastName;

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "passwd")
    WebElement passwordField;

    @FindBy(id = "years")
    WebElement years;

    @FindBy(id = "months")
    WebElement months;

    @FindBy(id = "days")
    WebElement days;

    @FindBy(id = "newsletter")
    WebElement newsletter;

    @FindBy(id = "optin")
    WebElement specialOffers;

    @FindBy(id = "firstname")
    WebElement addressFirstName;

    @FindBy(id = "lastname")
    WebElement addressLastName;

    @FindBy(id = "company")
    WebElement addressCompany;

    @FindBy(id = "address1")
    WebElement addressLine1;

    @FindBy(id = "address2")
    WebElement addressLine2;

    @FindBy(id = "city")
    WebElement addressCity;

    @FindBy(id = "id_state")
    WebElement addressState;

    @FindBy(id = "postcode")
    WebElement addressPostcode;

    @FindBy(id = "other")
    WebElement addressOther;

    @FindBy(id = "phone")
    WebElement addressPhone;

    @FindBy(id = "phone_mobile")
    WebElement addressMobile;

    @FindBy(id = "alias")
    WebElement addressAlias;

    @FindBy(id = "submitAccount")
    WebElement submitAccount;

    @FindBy(id = "SubmitLogin")
    WebElement submitLogin;

    public void createAccount(String firstName, String lastName, String email, String password) {
        //Create an account
        emailCreate.sendKeys(email);
        submitCreate.click();
        //Title
        gender.click();
        //First name
        customerFirstName.sendKeys(firstName);
        //Last name
        customerLastName.sendKeys(lastName);
        //Email
        wait.until(ExpectedConditions.attributeToBe(emailField, "value", email));
        //Password
        passwordField.sendKeys(password);
        //Date of Birth
        selectValue(years, 2);
        selectValue(months, 1);
        selectValue(days, 1);
        //Newsletter
        newsletter.click();
        //Special offers
        specialOffers.click();
        //Your Address
        wait.until(ExpectedConditions.attributeToBe(addressFirstName,"value",firstName));
        wait.until(ExpectedConditions.attributeToBe(addressLastName,"value",lastName));
        addressCompany.sendKeys("Test Co.");
        addressLine1.sendKeys("Selenium St. 123");
        addressLine2.sendKeys("Building C Penthouse");
        addressCity.sendKeys("Kosciuszko");
        selectValue(addressState, 1);
        addressPostcode.sendKeys("12345");
        addressOther.sendKeys("Selenium test client");
        addressPhone.sendKeys("(123) 456-7890");
        addressMobile.sendKeys("123-456-7890");
        addressAlias.clear();
        addressAlias.sendKeys("Test Address");
        //Register
        submitAccount.click();
    }

    public void signIn(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        submitLogin.click();
    }

    private void selectValue(WebElement webElement, int startFrom) {
        Select select = new Select(webElement);
        List<WebElement> selectList = select.getOptions();
        int selectSize = selectList.size();
        int randomIndex = ThreadLocalRandom.current().nextInt(startFrom, selectSize);
        select.selectByIndex(randomIndex);
    }
}
