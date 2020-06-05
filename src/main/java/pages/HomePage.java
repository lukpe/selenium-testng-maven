package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    public WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//img[@class='logo img-responsive']")
    WebElement logo;

    @FindBy(xpath = "//input[@id='search_query_top']")
    WebElement searchBar;

    @FindBy(xpath = "//div[@class='shopping_cart']")
    WebElement shoppingCart;

    @FindBy(xpath = "//a[@class='login']")
    WebElement signIn;

    public Boolean checkLogo() {
        return logo.isDisplayed();
    }

    public Boolean checkSearchBar() {
        return searchBar.isDisplayed();
    }

    public Boolean checkShoppingCart() {
        return shoppingCart.isDisplayed();
    }

    public Boolean checkSignIn(){
        return signIn.isDisplayed();
    }
}
