package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver driver;
    Actions actions;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//img[@class='logo img-responsive']")
    WebElement logo;

    @FindBy(xpath = "//input[@id='search_query_top']")
    WebElement searchBar;

    @FindBy(xpath = "//div[@class='shopping_cart']/a")
    WebElement shoppingCart;

    @FindBy(xpath = "//p[@class='cart-buttons']//a[@id='button_order_cart']")
    WebElement checkOut;

    @FindBy(xpath = "//a[@class='login']")
    WebElement login;

    @FindBy(xpath = "//a[@class='logout']")
    WebElement logout;

    public String getPageTitle() {
        return driver.getTitle();
    }

    public WebElement getLogo() {
        return logo;
    }

    public WebElement getSearchBar() {
        return searchBar;
    }

    public WebElement getShoppingCart() {
        return shoppingCart;
    }

    public WebElement getSignIn() {
        return login;
    }

    public void signIn() {
        login.click();
    }

    public void signOut() {
        logout.click();
    }

    public void searchProduct(String input) {
        searchBar.sendKeys(input);
        searchBar.submit();
    }

    public String getCartQuantity() {
        return shoppingCart.getText();
    }

    public void proceedCheckOut() {
        new Actions(driver).pause(500).moveToElement(shoppingCart).pause(500).moveToElement(checkOut).click().perform();
    }
}
