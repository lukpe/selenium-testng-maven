package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.ExcelDriver;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class LoginPage {
    public WebDriver driver;
    private final WebDriverWait wait;
    private final ExcelDriver excel;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        excel = new ExcelDriver();
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

    public void createAccount() {
        Map<String, String> accountData = new TreeMap<>();

        String firstName = "Jimmy";
        accountData.put("firstname", firstName);
        String lastName = "Page";
        accountData.put("lastname", lastName);

        //Generate email and password
        String dateNum = String.valueOf(System.currentTimeMillis());
        accountData.put("email", firstName.toLowerCase() + lastName.toLowerCase() + dateNum + "@selenium.test");
        accountData.put("password", "Test" + dateNum + "!");

        //Create an account
        emailCreate.sendKeys(accountData.get("email"));
        submitCreate.click();
        //Title
        gender.click();
        //First name
        customerFirstName.sendKeys(firstName);
        //Last name
        customerLastName.sendKeys(lastName);
        //Email
        wait.until(ExpectedConditions.attributeToBe(emailField, "value", accountData.get("email")));
        //Password
        passwordField.sendKeys(accountData.get("password"));
        //Date of Birth
        selectValue(years, 2);
        String month = selectValue(months, 1);
        int day = Integer.parseInt(selectValue(days, 1));
        //Quick fix for February days > 28 and other months days > 30
        if (month.contains("February")) {
            while (day > 28) {
                day = Integer.parseInt(selectValue(days, 1));
            }
        } else {
            while (day > 30) {
                day = Integer.parseInt(selectValue(days, 1));
            }
        }
        //Newsletter
        newsletter.click();
        //Special offers
        specialOffers.click();
        //Your Address
        wait.until(ExpectedConditions.attributeToBe(addressFirstName, "value", firstName));
        wait.until(ExpectedConditions.attributeToBe(addressLastName, "value", lastName));

        accountData.put("company", "Test Co.");
        addressCompany.sendKeys(accountData.get("company"));

        accountData.put("address1", "Selenium St. " + getRndInt(100, 999));
        addressLine1.sendKeys(accountData.get("address1"));

        accountData.put("address2", "Building C" + getRndInt(1, 99) + " Penthouse");
        addressLine2.sendKeys(accountData.get("address2"));

        accountData.put("city", "Kosciusko");
        addressCity.sendKeys(accountData.get("city"));

        selectValue(addressState, 1);
        Select select = new Select(addressState);
        WebElement option = select.getFirstSelectedOption();
        accountData.put("id_state", option.getText());

        accountData.put("id_country", "United States");

        accountData.put("postcode", String.valueOf(getRndInt(10000, 99999)));
        addressPostcode.sendKeys(accountData.get("postcode"));

        addressOther.sendKeys("Selenium test client");

        accountData.put("phone", "(" + getRndInt(100, 999) + ")" + getRndInt(100, 999) + "-" + getRndInt(1000, 9999));
        addressPhone.sendKeys(accountData.get("phone"));

        accountData.put("phone_mobile", getRndInt(100, 999) + "-" + getRndInt(100, 999) + "-" + getRndInt(1000, 9999));
        addressMobile.sendKeys(accountData.get("phone_mobile"));

        addressAlias.clear();
        addressAlias.sendKeys("Test Address");
        //Register
        submitAccount.click();

        excel.addNewRow();
        for (Map.Entry<String, String> entry : accountData.entrySet()) {
            excel.setColumnValue(entry.getKey(), entry.getValue());
        }
    }

    public void signIn() {
        try {
            emailField.sendKeys(excel.getColumnValue("email"));
            passwordField.sendKeys(excel.getColumnValue("password"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        submitLogin.click();
    }

    private String selectValue(WebElement webElement, int startFrom) {
        Select select = new Select(webElement);
        List<WebElement> selectList = select.getOptions();
        int selectSize = selectList.size();
        int randomIndex = ThreadLocalRandom.current().nextInt(startFrom, selectSize);
        select.selectByIndex(randomIndex);
        return select.getFirstSelectedOption().getText().trim();
    }

    private static int getRndInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
