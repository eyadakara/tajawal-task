package com.tajawal.screens;

import com.tajawal.drivers.AndroidDrive;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Getter
@Setter
public class ListingScreen {

    @AndroidFindBy(id = "quickActionsContainer")
    private AndroidElement quickFiltersContainer;
    @AndroidFindBy(id = "tvTitleStateView")
    private AndroidElement noResultsLabel;
    @AndroidFindBy(id = "rvListBottomSheet")
    private AndroidElement sortBySheet;
    @AndroidFindBy(id = "tvPriceRangeBarFrom")
    private AndroidElement minPriceRange;
    @AndroidFindBy(id = "tvFinalPrice")

    private List<AndroidElement> finalPrice;
    private float firstFinalPrice;
    private String finalPriceFromFilter;
    private AndroidDrive driver;

    public ListingScreen(AndroidDrive driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean sortTripsByLowestPrice() {
        try {
            driver.waitVisibility(quickFiltersContainer, 30);

            List<MobileElement> quickFilters = quickFiltersContainer.findElements(By.id("tabQuickFilter"));
            MobileElement sortBy = quickFilters.get(0);
            sortBy.click();

            List<MobileElement> sortOptionsSheet = sortBySheet.findElements(By.id("ltSheetName"));
            MobileElement lowestPrice = sortOptionsSheet.get(0);
            lowestPrice.click();
            return true;
        } catch (Exception ignored) {
            Assert.assertEquals(noResultsLabel.getText(), "No results");
            return false;
        }
    }

    public void saveFinalPrice() {
        setFirstFinalPrice(parsePrice(finalPrice.get(0).getText()));
    }

    public float getFinalPriceFromFilter() {
        List<MobileElement> quickFilters = quickFiltersContainer.findElements(By.id("tabQuickFilter"));
        MobileElement filters = quickFilters.get(1);
        filters.click();
        return parsePrice(minPriceRange.getText());
    }

    /**
     * @param price price with currency
     * @return parsed price as float without currency
     */
    public float parsePrice(String price) {
        return Float.parseFloat(price.replaceAll("[^\\d.]+|\\.(?!\\d)", "")); }
}