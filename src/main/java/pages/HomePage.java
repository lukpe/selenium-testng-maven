package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    public WebDriver driver;
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

    public boolean checkLogo() {
        return logo.isDisplayed();
    }

    public boolean checkSearchBar() {
        return searchBar.isDisplayed();
    }

    public boolean checkShoppingCart() {
        return shoppingCart.isDisplayed();
    }

    public boolean checkSignIn() {
        return login.isDisplayed();
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

    public boolean checkCartQuantity(String quantity) {
        return shoppingCart.getText().matches("^Cart " + quantity + " Products?$");
    }

    public void proceedCheckOut(){
        new Actions(driver).pause(500).moveToElement(shoppingCart).pause(500).moveToElement(checkOut).click().perform();
    }
}
