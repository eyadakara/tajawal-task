package com.tajawal.screens;

import com.tajawal.drivers.AndroidDrive;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.support.PageFactory;

@Getter
@Setter
public class OnBoardingScreen {

    @AndroidFindBy(id = "welcomeEnglishButton")
    private AndroidElement welcomeEnglishButton;
    @AndroidFindBy(id = "ctaText")

    private AndroidElement continueButton;
    private AndroidDrive driver;

    public OnBoardingScreen(AndroidDrive driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public void selectEnglishLanguage() {
        welcomeEnglishButton.click();
    }

    public void clickOnContinueButton() {
        continueButton.click();
    }
}