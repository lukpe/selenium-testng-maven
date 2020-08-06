package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SummaryPage {
    public WebDriver driver;
    private final WebDriverWait wait;


    public SummaryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[@id='cart_title']")
    WebElement cartTitle;

    @FindBy(xpath = "//td[@class='cart_description']//p[@class='product-name']")
    WebElement productName;

    @FindBy(css = ".cart_quantity_input.form-control.grey")
    WebElement productQuantity;

    @FindBy(xpath = "//i[@class='icon-plus']")
    WebElement iconPlus;

    @FindBy(xpath = "//p//a[@title='Proceed to checkout']")
    WebElement checkOut;

    public boolean verifyProductQtyTitle(int quantity) {
        String ending = "";
        if (quantity > 1) {
            ending = "s";
        }
        wait.until(ExpectedConditions.visibilityOf(cartTitle));
        return cartTitle.getText().contentEquals("SHOPPING-CART SUMMARY\nYour shopping cart contains: "
                + quantity + " Product" + ending);
    }

    public boolean verifyProductName(String input) {
        return productName.getText().matches(".*(?i)" + input + "(?-i).*");
    }

    public boolean verifyProductQty(int quantity) {
        return productQuantity.getAttribute("value").contentEquals(String.valueOf(quantity));
    }

    public void addProduct(int quantity) {
        for (int i = 1; i < quantity; i++) {
            new Actions(driver).moveToElement(iconPlus).click().pause(2000).perform();
        }
    }

    public void proceedCheckOut() {
        checkOut.click();
    }
}
