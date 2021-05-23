# Tajawal
In this task you will find the mentioned test scenarios in Android using appium.

### Main Test Scenario:
- [x] 1- Open Tajawal or Almosafer Native Mobile App

- [x] 2- Check for current set language. If language is already set to english then proceed with next steps. If not, then first change language to english and then proceed.

- [x] 3- Navigate to flights-home page, and enter below criteria in flights search form to make flight search:
    - Origin - (from random array of origins - length 5) (Example: DXB, AUH, SHJ, JED, RUH)
    - Destination - (from random array of destinations - length 5) (Example: AMM, CAI, DEL, KHI, PAR)
    - Depart and Return Dates (randomly generated dates in future. Do not select date in current month)
    - Passengers (1Adult)
    - Cabin Class (Economy)

- [x] 4- Wait for loading to be completed on flight listing page

- [x] 5- Assert the Dates in the flight listing page on the header

- [x] 5- Use sort feature to ensure that flights are sorted by 'cheapest'

- [x] 6- After loading is completed, fetch and save price of first flight

- [x] 7- Assert the minimum-price displayed for price-range-filter equals to price of first-flight in list

# Result:
![Alt Text](./results/results.gif)
