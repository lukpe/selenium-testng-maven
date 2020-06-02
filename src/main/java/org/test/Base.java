package org.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Base {

    public WebDriver driver;
    public Properties properties;

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
                if (browserName.contains("headless")) {
                    options.setHeadless(true);
                }
                driver = new ChromeDriver(options);
            } else if (browserName.contains("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (browserName.contains("headless")) {
                    options.setHeadless(true);
                }
                driver = new FirefoxDriver(options);
            }
        }

        //Default timeout
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
