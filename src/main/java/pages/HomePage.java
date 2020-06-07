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

}
