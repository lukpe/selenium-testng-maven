package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.ExcelDriver;

public class MyAccountPage {
    public WebDriver driver;
    private final WebDriverWait wait;
    private final ExcelDriver excel;
    private String firstName;
    private String lastName;
    private String email;

    public MyAccountPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        excel = new ExcelDriver();
    }

    @FindBy(className = "page-heading")
    WebElement pageHeader;

    @FindBy(xpath = "//span[contains(text(),'My personal information')]")
    WebElement personalInformation;

    @FindBy(id = "firstname")
    WebElement accountFirstName;

    @FindBy(id = "lastname")
    WebElement accountLastName;

    @FindBy(id = "email")
    WebElement accountEmail;

    @FindBy(xpath = "//a[@title='Home']")
    WebElement home;


    public boolean verifyPageHeader() {
        wait.until(ExpectedConditions.visibilityOf(pageHeader));
        return pageHeader.getText().contentEquals("MY ACCOUNT");
    }

    public boolean verifyPersonalInformation() {
        try {
            firstName = excel.getValueByColumnName("firstname");
            lastName = excel.getValueByColumnName("lastname");
            email = excel.getValueByColumnName("email");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        personalInformation.click();
        boolean fNameCorrect = accountFirstName.getAttribute("value").equalsIgnoreCase(firstName);
        boolean lNameCorrect = accountLastName.getAttribute("value").equalsIgnoreCase(lastName);
        boolean emailCorrect = accountEmail.getAttribute("value").equalsIgnoreCase(email);
        return fNameCorrect && lNameCorrect && emailCorrect;
    }
}
