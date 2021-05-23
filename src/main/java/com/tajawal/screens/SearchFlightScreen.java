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
import java.util.Random;

@Getter
@Setter
public class SearchFlightScreen {
    @AndroidFindBy(id = "flightsCtaView")
    private AndroidElement flightsButton;
    @AndroidFindBy(id = "originView")
    private AndroidElement originField;
    @AndroidFindBy(id = "destinationView")
    private AndroidElement destinationField;
    @AndroidFindBy(id = "edSearch")
    private AndroidElement searchField;
    @AndroidFindBy(id = "tvAirportCode")
    private AndroidElement airportCode;
    @AndroidFindBy(id = "rvSearchAirports")
    private AndroidElement airportList;
    @AndroidFindBy(id = "flightPaxView")
    private AndroidElement passengersAndCabinClassField;
    @AndroidFindBy(id = "tvPaxCount")
    private AndroidElement numOfAdults;
    @AndroidFindBy(id = "rvCabinView")
    private AndroidElement cabinClassList;
    @AndroidFindBy(id = "applyMenu")
    private AndroidElement applyButton;
    @AndroidFindBy(id = "paxCardView")
    private AndroidElement passengersCardView;
    @AndroidFindBy(id = "calendarView")
    private AndroidElement calendarView;
    @AndroidFindBy(id = "checkIn")
    private AndroidElement checkIn;
    @AndroidFindBy(id = "checkOut")
    private AndroidElement checkOut;
    @AndroidFindBy(id = "confirmBtnGroup")
    private AndroidElement confirmButton;
    @AndroidFindBy(id = "btnFlightSearch")
    private AndroidElement findFlightsButton;
    @AndroidFindBy(id = "tvFlightToolbarSubTitle")
    private AndroidElement toolBarSubTitle;
    @AndroidFindBy(id = "quickActionsContainer")
    private AndroidElement quickFiltersContainer;

    private String departureDate;
    private String returnDate;
    private String departureDateResult;
    private String returnDateResult;
    private String[] originFlights = {"DXB", "AUH", "SHJ", "JED", "RUH"};
    private String[] destinationFlights = {"AMM", "CAI", "DEL", "KHI", "PAR"};
    private AndroidDrive driver;

    public SearchFlightScreen(AndroidDrive driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public void clickOnFlightsButton() {
        driver.waitVisibility(getFlightsButton(), 10);
        flightsButton.click();
    }

    public void fillOriginAndDestination() {
        driver.waitVisibility(getOriginField(), 5);
        clickOnOriginField();
        selectSpecificAirport(originFlights);
        selectSpecificAirport(destinationFlights);
    }

    public void setPassengersAndCabinClass() {
        clickOnPassengersAndCabinClassField();
        setEconomyCabinClass();
        setAdultIsOneAndOtherPassengersAreZero();
        clickOnApplyButton();
    }

    public String getPassengersAndCabinClassResult() {
        return passengersAndCabinClassField.findElement(By.id("tvItemText")).getText();
    }

    /**
     *  In this method, I selected the next month, then I've selected random departure & return values from the next month,
     *  I saved the two values to be asserted with the departure and return dates in the toolbar in the listing page.
     */
    public void selectDateRange() {
        clickOnCheckInField();
        List<MobileElement> visibleMonths = calendarView.findElements(By.className("e.a.a.g.t.j.a"));
        MobileElement nextMonth = visibleMonths.get(1);
        List<MobileElement> allDays = nextMonth.findElements(By.className("android.widget.CheckedTextView"));
        int dayOne = new Random().nextInt(allDays.size());
        int dayTwo = new Random().nextInt(allDays.size());
        int from = Math.min(dayOne, dayTwo);
        int to = Math.max(dayOne, dayTwo);
        allDays.get(from).click();
        allDays.get(to).click();
        clickOnConfirmButton();
        departureDate = checkIn.findElement(By.id("tvItemText")).getText().split(",")[0].trim();
        returnDate = checkOut.findElement(By.id("tvItemText")).getText().split(",")[0].trim();
        findFlightsButton.click();
        departureDateResult = toolBarSubTitle.getText().split("-")[1].trim();
        returnDateResult = toolBarSubTitle.getText().split("-")[2].trim();
    }

    private void clickOnConfirmButton() {
        confirmButton.click();
    }

    private void clickOnCheckInField() {
        checkIn.click();
    }

    private void clickOnOriginField() {
        originField.click();

    }

    /**
     *  I made here recursion method to keep looping in the minus buttons of (Adults, Children and Infants) all disabled
     *  to match the default behavior
     */
    private void setAdultIsOneAndOtherPassengersAreZero() {
        boolean isMinusButtonEnabled = true;
        List<MobileElement> minusPassengersButtons = passengersCardView.findElements(By.id("imgRemovePax"));
        for (MobileElement minusPassengersButton : minusPassengersButtons) {
            if (minusPassengersButton.isEnabled()) {
                minusPassengersButton.click();
                isMinusButtonEnabled = false;
            }
        }
        if (isMinusButtonEnabled) {
            return;
        }
        setAdultIsOneAndOtherPassengersAreZero();
    }

    private void setEconomyCabinClass() {
        cabinClassList.findElements(By.id("rdCabinItem")).get(0).click();
    }

    private void clickOnApplyButton() {
        applyButton.click();
    }

    private void clickOnPassengersAndCabinClassField() {
        passengersAndCabinClassField.click();
    }

    /**
     *   in this method, when searching for a random airport code from the mentioned arrays, I've covered all possibilities
     *         might occur, like when the airport code is exist, then we will click on the airport code in the search result,
     *         and while I am testing the scenario, it seems "PAR" airport code doesn't return in the search result at all, so
     *         in this case I made a workaround to click in the first airport code to be able to proceed.
     * @param array array of string for any fields origin or destination to select airport code randomly.
     */
    private void selectSpecificAirport(String[] array) {
        clickOnSearchField();
        String currentRandAirportCode = getRandom(array);
        fillSearchField(currentRandAirportCode);
        List<MobileElement> airportCodes = airportList.findElements(By.id("tvAirportCode"));
        MobileElement searchedElement = null;
        for (MobileElement code : airportCodes) {
            if (code.getText().equals(currentRandAirportCode)) {
                searchedElement = code;
                break;
            }
        }
        if (searchedElement != null) {
            searchedElement.click();

        } else if (!airportCodes.isEmpty()) {
            airportCodes.get(0).click();
        } else {
            Assert.fail("The Search list is empty!");
        }
    }

    private String getRandom(String[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    private void clickOnSearchField() {
        driver.explicitWaitById(2, "edSearch");
        this.searchField.click();
    }

    private void fillSearchField(String searchInput) {
        this.searchField.setValue(searchInput);
    }
}