package pages;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.ExcelDriver;

public class SummaryPage {
    private final WebDriver driver;
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

    @FindBy(xpath = "//td[@id='total_shipping']")
    WebElement totalShipping;

    @FindBy(xpath = "//td[@id='total_tax']")
    WebElement totalTax;

    @FindBy(xpath = "//td[@id='total_price_without_tax']")
    WebElement totalPriceNoTax;

    @FindBy(xpath = "//span[@id='total_price']")
    WebElement totalPrice;

    @FindBy(xpath = "//p//a[@title='Proceed to checkout']")
    WebElement checkOut;

    public String getProductQtyTitle() {
        wait.until(ExpectedConditions.visibilityOf(cartTitle));
        return cartTitle.getText();
    }

    public void verifyProductName(String input) {
        try {
            if (!productName.getText().matches(".*(?i)" + input + "(?-i).*")) {
                throw new ElementNotVisibleException("product name not found: " + input);
            }
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        }
    }

    public String getProductQty() {
        return productQuantity.getAttribute("value");
    }

    public void addProduct(int quantity) {
        if (quantity > 1) {
            for (int i = 1; i < quantity; i++) {
                new Actions(driver).moveToElement(iconPlus).click().pause(2000).perform();
            }
        }
    }

    public boolean verifyTotalPrice() {
        ExcelDriver excel = new ExcelDriver();
        double productPrice = Double.parseDouble(excel.getColumnValue("product_price"));
        int productQty = Integer.parseInt(excel.getColumnValue("product_quantity"));
        double shipping = getDoubleValue(totalShipping);
        double tax = getDoubleValue(totalTax);
        double currentTotalNoTax = getDoubleValue(totalPriceNoTax);
        double currentTotal = getDoubleValue(totalPrice);
        double correctTotalNoTax = productPrice * productQty + shipping;
        double correctTotal = correctTotalNoTax + tax;
        if (currentTotalNoTax != correctTotalNoTax) {
            throw new ArithmeticException("Total without tax expected: " + correctTotalNoTax +
                    ", got: " + currentTotalNoTax);
        } else if (currentTotal != correctTotal) {
            throw new ArithmeticException("Total with tax expected: " + correctTotal +
                    ", got: " + currentTotal);
        } else {
            return true;
        }
    }

    private double getDoubleValue(WebElement webElement) {
        return Double.parseDouble(webElement.getText().replace("$", ""));
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public String getTotalShipping() {
        return totalShipping.getText();
    }

    public void proceedCheckOut() {
        checkOut.click();
    }
}
