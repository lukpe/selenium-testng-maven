package org.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Base {

    public WebDriver driver;
    public Properties properties;
    public int timeOut;

    public WebDriver initializeDriver() throws IOException {
        properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\resources\\test.properties");
        properties.load(fis);

        //Browser selection
        String browserName = System.getProperty("browser");
        if (browserName == null) {
            browserName = properties.getProperty("browser");
        }

        //RemoteDriver
        if (browserName.contains("remote")) {
            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setPlatform(Platform.LINUX);
            if (browserName.contains("chrome")) {
                dc.setBrowserName("chrome");
            } else if (browserName.contains("firefox")) {
                dc.setBrowserName("firefox");
            }
            driver = new RemoteWebDriver(new URL(properties.getProperty("gridURL") + "/wd/hub"), dc);
        } else {
            //Local browser
            if (browserName.contains("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                driver = new ChromeDriver(options);
            } else if (browserName.contains("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                driver = new FirefoxDriver(options);
            }
        }

        //Default timeout
        timeOut = Integer.parseInt(properties.getProperty("timeOut"));
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

        driver.get(properties.getProperty("homePageURL"));
        driver.manage().window().maximize();

        return driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
