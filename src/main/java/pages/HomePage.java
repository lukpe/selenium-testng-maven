package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    public WebDriver driver;
    private final WebDriverWait wait;
    Actions actions;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
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

    public boolean verifyPageTitle(String title) {
        return driver.getTitle().contentEquals(title);
    }

    public boolean verifyLogo() {
        return logo.isDisplayed();
    }

    public boolean verifySearchBar() {
        return searchBar.isDisplayed();
    }

    public boolean verifyShoppingCart() {
        return shoppingCart.isDisplayed();
    }

    public boolean verifySignIn() {
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

    public void proceedCheckOut() {
        new Actions(driver).pause(500).moveToElement(shoppingCart).pause(500).moveToElement(checkOut).click().perform();
    }
}
