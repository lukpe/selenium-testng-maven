package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
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

    public String[][] createAccount(String firstName, String lastName, String email, String password) {
        String[][] clientData = new String[11][2];
        //Create an account
        emailCreate.sendKeys(email);
        submitCreate.click();
        //Title
        gender.click();
        //First name
        clientData[0][0] = "firstname";
        clientData[0][1] = firstName;
        customerFirstName.sendKeys(clientData[0][1]);
        //Last name
        clientData[1][0] = "lastname";
        clientData[1][1] = lastName;
        customerLastName.sendKeys(clientData[1][1]);
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

        Random rand = new Random();

        clientData[2][0] = "company";
        clientData[2][1] = "Test Co.";
        addressCompany.sendKeys(clientData[2][1]);

        clientData[3][0] = "address1";
        clientData[3][1] = "Selenium St. " + getRndInt(100,999);
        addressLine1.sendKeys(clientData[3][1]);

        clientData[4][0] = "address2";
        clientData[4][1] = "Building C" + getRndInt(1,99) + " Penthouse";
        addressLine2.sendKeys(clientData[4][1]);

        clientData[5][0] = "city";
        clientData[5][1] = "Kosciusko";
        addressCity.sendKeys(clientData[5][1]);

        selectValue(addressState, 1);
        Select select = new Select(addressState);
        WebElement option = select.getFirstSelectedOption();
        clientData[6][0] = "id_state";
        clientData[6][1] = option.getText();

        clientData[7][0] = "id_country";
        clientData[7][1] = "United States";

        clientData[8][0] = "postcode";
        clientData[8][1] = String.valueOf(getRndInt(10000,99999));
        addressPostcode.sendKeys(clientData[8][1]);

        addressOther.sendKeys("Selenium test client");

        clientData[9][0] = "phone";
        clientData[9][1] = "(" + getRndInt(100,999) + ")" + getRndInt(100,999) + "-" + getRndInt(1000,9999);
        addressPhone.sendKeys(clientData[9][1]);

        clientData[10][0] = "phone_mobile";
        clientData[10][1] = getRndInt(100,999) + "-" + getRndInt(100,999) + "-" + getRndInt(1000,9999);
        addressMobile.sendKeys(clientData[10][1]);

        addressAlias.clear();
        addressAlias.sendKeys("Test Address");
        //Register
        submitAccount.click();

        return clientData;
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

    private static int getRndInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
