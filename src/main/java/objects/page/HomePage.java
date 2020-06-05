package objects.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    public WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private final By logo = By.xpath("//img[@class='logo img-responsive']");
    private final By searchBar = By.xpath("//input[@id='search_query_top']");
    private final By shoppingCart = By.xpath("//div[@class='shopping_cart']");
    private final By singInLink = By.xpath("//a[@class='login']");

    public WebElement getLogo() {
        return driver.findElement(logo);
    }

    public WebElement getSearchBar() {
        return driver.findElement(searchBar);
    }

    public WebElement getShoppingCart() {
        return driver.findElement(shoppingCart);
    }

    public WebElement getSignInLink() {
        return driver.findElement(singInLink);
    }

}
