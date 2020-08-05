package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.test.ExcelDriver;

public class MyAccountPage {
    public WebDriver driver;
    private final ExcelDriver excel;
    private String firstName;
    private String lastName;
    private String email;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        excel = new ExcelDriver();
    }

    @FindBy(xpath = "//span[contains(text(),'My personal information')]")
    WebElement personalInformation;

    @FindBy(id = "firstname")
    WebElement accountFirstName;

    @FindBy(id = "lastname")
    WebElement accountLastName;

    @FindBy(id = "email")
    WebElement accountEmail;

    public boolean verifyPersonalInformation() {
        try {
            firstName = excel.getColumnValue("firstname");
            lastName = excel.getColumnValue("lastname");
            email = excel.getColumnValue("email");
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
