Feature: Setup DiveSafe program information (p10)
  As the administrator, I want to setup the DiveSafe program information

  Background: 
    Given a DiveSafe system exists with defaults values "2020-12-31" as start date, "0" for number of days, and "0" for price of guide per day. (p10)

  Scenario Outline: Setup DiveSafe program information successfully
    When the admin attempts to setup the DiveSafe program information with the start date "2022-01-13", number of diving days "20", and price of guide per day "50" (p10)
    Then the DiveSafe program information shall be successfully setup with the start date "2022-01-13", number of diving days "20", and price of guide per day "50" (p10)

  Scenario Outline: Setup DiveSafe program information with invalid inputs
    When the admin attempts to setup the DiveSafe program information with the start date "<startDate>", number of diving days "<numDays>", and price of guide per day "<priceOfGuidePerDay>" (p10)
    Then the following "<error>" shall be raised. (p10)

    Examples: 
      | startDate  | numDays | priceOfGuidePerDay | error                                                            |
      | 2022-01-13 |      -1 |                 50 | The number of diving days must be greater than or equal to zero  |
      | 2022-01-13 |      20 |                 -1 | The price of guide per day must be greater than or equal to zero |
      | 2021-31-31 |      20 |                 50 | Invalid date                                                     |
