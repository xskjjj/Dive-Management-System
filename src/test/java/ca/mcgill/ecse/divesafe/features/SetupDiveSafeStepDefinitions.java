package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.SetupController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SetupDiveSafeStepDefinitions {

  private DiveSafe diveSafe;
  private String error = "";

  /**
   * @author Andrew Geday, Andrei Cosmovici
   */
  @Given("a DiveSafe system exists with defaults values {string} as start date, {string} for number of days, and {string} for price of guide per day. \\(p10)")
  public void a_dive_safe_system_exists_with_defaults_values_as_start_date_for_number_of_days_and_for_price_of_guide_per_day_p10(
      String date, String days, String price) {
    error = "";

    diveSafe = DiveSafeApplication.getDiveSafe();
    diveSafe.setStartDate(Date.valueOf(date));
    diveSafe.setNumDays(Integer.parseInt(days));
    diveSafe.setPriceOfGuidePerDay(Integer.parseInt(price));
  }

  /**
   * @author Eric Xia, Gwynette Labitoria
   */
  @Then("the DiveSafe program information shall be successfully setup with the start date {string}, number of diving days {string}, and price of guide per day {string} \\(p10)")
  public void the_nmc_program_information_shall_be_successfully_setup_with_the_start_date_number_of_diving_days_and_price_of_guide_per_day_p10(
      String date, String days, String price) {
    assertEquals(date, String.valueOf(diveSafe.getStartDate()));
    assertEquals(days, String.valueOf(diveSafe.getNumDays()));
    assertEquals(price, String.valueOf(diveSafe.getPriceOfGuidePerDay()));

  }

  /**
   * @author Andrew Geday, Jerry Hou-Liu
   */
  @When("the admin attempts to setup the DiveSafe program information with the start date {string}, number of diving days {string}, and price of guide per day {string} \\(p10)")
  public void the_admin_attempts_to_setup_the_nmc_program_inforamtion_with_the_start_date_number_of_diving_days_and_price_of_guide_per_day_p10(
      String date, String days, String price) {

    try {
      Date d = Date.valueOf(date);
      int numDays = Integer.parseInt(days);
      int p = Integer.parseInt(price);
      error += SetupController.setup(d, numDays, p);
    } catch (IllegalArgumentException e2) {
      error = "Invalid date";
    }

  }

  /**
   * @author Ethan Cheng, Andrei Cosmovici
   */
  @Then("the following {string} shall be raised. \\(p10)")
  public void the_following_shall_be_raised_p10(String expError) {
    assertTrue(error.contains(expError));
  }
}
