package com;

import com.tajawal.drivers.AndroidDrive;
import com.tajawal.screens.ListingScreen;
import com.tajawal.screens.OnBoardingScreen;
import com.tajawal.screens.SearchFlightScreen;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest {

    private AndroidDrive driver;
    private OnBoardingScreen onBoardingScreen;
    private ListingScreen listingScreen;
    private SearchFlightScreen searchFlightScreen;

    @BeforeClass
    public void setup() {
        driver = new AndroidDrive();
        driver.setup();
        listingScreen = new ListingScreen(driver);
        searchFlightScreen = new SearchFlightScreen(driver);
        onBoardingScreen = new OnBoardingScreen(driver);
    }

    @Test(description = "Verify that the app can be changed to English successfully.")
    public void verifyTheAppLanguage() {
        onBoardingScreen.selectEnglishLanguage();
        Assert.assertTrue(onBoardingScreen.getWelcomeEnglishButton().isSelected(), "The app language is English");
    }

    @Test(description = "verify the number of passengers is One and the cabin class is Economy",
            dependsOnMethods = "verifyTheAppLanguage",
            priority = 1)
    public void verifyPassengersAndCabinClass() {
        onBoardingScreen.clickOnContinueButton();
        searchFlightScreen.clickOnFlightsButton();
        searchFlightScreen.fillOriginAndDestination();
        searchFlightScreen.setPassengersAndCabinClass();
        Assert.assertEquals(searchFlightScreen.getPassengersAndCabinClassResult(), "1 Adult - Economy");
    }

    @Test(description = "verify the departure and return dates are getting reflected in listing page successfully",
            dependsOnMethods = "verifyPassengersAndCabinClass",
            priority = 2)
    public void verifyDepartureAndReturnDates() {
        searchFlightScreen.selectDateRange();
        Assert.assertEquals(searchFlightScreen.getDepartureDate(), searchFlightScreen.getDepartureDateResult());
        Assert.assertEquals(searchFlightScreen.getReturnDate(), searchFlightScreen.getReturnDateResult());
    }

    @Test(description = "verify after sorting by the lowest price, the first flight price matches the minimum price in Filters ",
            dependsOnMethods = "verifyDepartureAndReturnDates",
            priority = 3)
    public void verifyFirstFlight() {
        if (listingScreen.sortTripsByLowestPrice()) {
            listingScreen.saveFinalPrice();
            Assert.assertEquals(listingScreen.getFirstFinalPrice(), listingScreen.getFinalPriceFromFilter());
        }
    }

    @AfterClass()
    public void tearDown() {
        driver.tearDown();
    }
}
