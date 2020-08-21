package org.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    private WebDriver driver;

    public WebDriver initializeDriver() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty())
            browser = getProp("browser");
        browser = browser.toLowerCase();

        String remote = System.getProperty("remote");
        if (remote == null || remote.isEmpty())
            remote = "false";

        if (remote.equalsIgnoreCase("true")) {
            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setBrowserName(browser);
            try {
                driver = new RemoteWebDriver(new URL(getProp("gridURL")), dc);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name");
            }
        }
        driver.manage().timeouts().implicitlyWait(getTimeOut(), TimeUnit.SECONDS);

        driver.get(getProp("homePageURL"));
        driver.manage().window().maximize();

        return driver;
    }

    public String getProp(String name) {
        String propFile = System.getProperty("user.dir") + "\\src\\test\\resources\\test.properties";
        try (FileInputStream file = new FileInputStream(propFile)) {
            Properties prop = new Properties();
            prop.load(file);
            return prop.getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTimeOut() {
        return Integer.parseInt(getProp("timeOut"));
    }

    public WebDriver getDriver() {
        return driver;
    }
}
