package com.crawler.line.crawler.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Selenium {

    private WebDriver driver;

    public Selenium() {
        try {
            ChromeOptions option = new ChromeOptions();
            option.setHeadless(true);
            option.setProxy(null);
            option.addArguments(
                    "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            option.addArguments("Accept=\"text/html,application/xhtml+xml,application/xml;q=0.9,imgwebp,*/*;q=0.8");
            option.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

            this.driver = new RemoteWebDriver(new URL("http://localhost:44444/wd/hub"), option);
            this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (WebDriverException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        try {
            this.driver.quit();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(String url) {
        this.driver.navigate().to(url);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
