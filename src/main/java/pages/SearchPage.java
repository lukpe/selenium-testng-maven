package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.test.ExcelDriver;

import java.util.List;
import java.util.ListIterator;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    Actions actions;

    public SearchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@class='product_list grid row']//a[@class='product-name']")
    WebElement searchResult;

    private final By productPrice = new By.ByXPath("//ul[@class='product_list grid row']//span");

    @FindBy(xpath = "//span[contains(text(),'Add to cart')]")
    WebElement addToCart;

    @FindBy(xpath = "//div[@id='layer_cart']//div[1]/h2")
    WebElement header;

    @FindBy(xpath = "//span[@title='Continue shopping']")
    WebElement continueShopping;

    public String getSearchResult() {
        wait.until(ExpectedConditions.visibilityOf(searchResult));
        return searchResult.getAttribute("title").toLowerCase();
    }

    public String getMessageHeader() {
        wait.until(ExpectedConditions.visibilityOf(header));
        return header.getAttribute("innerText").trim();
    }

    public void addProductToCart() {
        new Actions(driver).pause(500).moveToElement(searchResult).pause(500).moveToElement(addToCart).click().perform();
    }

    public WebElement getContinueShopping() {
        return continueShopping;
    }

    public void saveProductDetails(String product, int quantity) {
        ExcelDriver excel = new ExcelDriver();
        excel.setColumnValue("product_name", product);
        excel.setColumnValue("product_price", getUnitPrice());
        excel.setColumnValue("product_quantity", String.valueOf(quantity));
    }

    private String getUnitPrice() {
        List<WebElement> spanList = driver.findElements(productPrice);
        ListIterator<WebElement> spanIterator = spanList.listIterator();
        String price = null;
        while (spanIterator.hasNext()) {
            price = spanIterator.next().getText();
            if (price.length() > 0) {
                price = price.replace("$", "");
                break;
            }
        }
        return price;
    }
}
