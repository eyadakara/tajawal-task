package com.tajawal.drivers;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDrive {

    protected AndroidDriver<MobileElement> driver;

    public void setup() {
        try {
            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver<>(url, getCapabilities());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        driver.quit();
    }

    public AndroidDriver<MobileElement> getDriver() {
        return driver;
    }

    public void waitVisibility(MobileElement elementBy, int time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), time);
        wait.until(ExpectedConditions.visibilityOf(elementBy));
    }

    public void explicitWaitById(int time, String id) {
        WebDriverWait wait = new WebDriverWait(getDriver(), time);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(id)));
    }

    private DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Galaxy S10");
        capabilities.setCapability("udid", "RF8M316W8BW");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("appPackage", "com.tajawal");
        capabilities.setCapability("appActivity", "com.travel.common.presentation.splash.SplashActivity");
        return capabilities;
    }
}