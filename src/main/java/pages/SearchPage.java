package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public SearchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@class='product_list grid row']//a[@class='product-name']")
    WebElement searchResult;

    @FindBy(xpath = "//span[contains(text(),'Add to cart')]")
    WebElement addToCart;

    @FindBy(xpath = "//div[@id='layer_cart']//div[1]/h2")
    WebElement header;

    @FindBy(xpath = "//span[@title='Continue shopping']")
    WebElement continueShopping;

    public boolean checkSearchResult(String name) {
        wait.until(ExpectedConditions.visibilityOf(searchResult));
        return searchResult.getAttribute("title").toLowerCase().contains(name);
    }

    public boolean verifyMessageHeader(String message) {
        wait.until(ExpectedConditions.visibilityOf(header));
        return header.getAttribute("innerText").trim().equalsIgnoreCase(message);
    }

    public void addProductToCart() {
        Action builder = actions.moveToElement(searchResult).moveToElement(addToCart).click().build();
        builder.perform();
    }

    public WebElement getContinueShopping() {
        return continueShopping;
    }
}
