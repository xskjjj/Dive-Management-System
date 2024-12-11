/*
 * written by:Ziyue Wang, Norman Kong, Alexander Koran, Saviru Perera, Farah Mehzabeen, Menad
 * Kessaci, Edwin You Zhou
 */

package ca.mcgill.ecse.divesafe.features;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.HotelController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Hotel;
import ca.mcgill.ecse.divesafe.model.Hotel.HotelType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddUpdateHotelStepDefinitions {

  private DiveSafe diveSafe;
  private String error;

  /**
   * This method creates diveSafe instance.
   *
   * @author Ziyue Wang
   */

  @Given("the following DiveSafe system exists: \\(p13)")
  public void the_following_dive_safe_system_exists_p13(io.cucumber.datatable.DataTable dataTable) {
    diveSafe = DiveSafeApplication.getDiveSafe();

    error = ""; // Initial error message set as empty


    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> columns : rows) {
      diveSafe.setStartDate(Date.valueOf(columns.get("startDate")));
      diveSafe.setPriceOfGuidePerDay(Integer.parseInt(columns.get("priceOfGuidePerDay")));
      diveSafe.setNumDays(Integer.parseInt(columns.get("numDays")));
    }
  }

  /**
   * This method create Hotel instances in diveSafe to construct environment for testing.
   *
   * @author Ziyue Wang
   */

  @Given("the following hotels exist in the system: \\(p13)")
  public void the_following_hotels_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    // get data and create Hotel
    for (Map<String, String> columns : rows) {
      if (Hotel.getWithName(columns.get("name")) == null) {
        HotelType type = HotelType.valueOf(columns.get("type"));
        diveSafe.addHotel(columns.get("name"), columns.get("address"),
            Integer.parseInt(columns.get("nrStars")), type);
      }
    }
  }

  /**
   * This method attemps to create new Hotel instances by calling controller.
   *
   * @param name, address, numStars
   * @author Norman Kong, Alexander Koran
   */

  @When("the admin attempts to add a new hotel to the system with {string}, {string}, {string}, and {string} \\(p13)")
  public void the_admin_attempts_to_add_a_new_hotel_to_the_system_with_and_p13(String name,
      String address, String numStars, String type) {
    callController(HotelController.addHotel(name, address,
        Integer.parseInt(numStars), type));
  }

  /**
   * This method asserts new hotel has been succesfully added to diveSafe and find corresponding.
   * hotel instance by its name, validate with its name, address rating params
   *
   * @author Ziyue Wang, Norman Kong, Alexander Koran, Farah Mehzabeen
   * @params name, address, numStars
   */

  @Then("the hotel with {string}, {string}, {string}, and {string} shall now exist in the system \\(p13)")
  public void the_hotel_with_and_shall_now_exist_in_the_system_p13(String name, String address,
      String numStars, String type) {
    Hotel current = Hotel.getWithName(name);
    assertNotNull(current);
    assertEquals(address, current.getAddress());
    assertEquals(Integer.parseInt(numStars), current.getRating());
    assertEquals(type, current.getType().toString());
    // Get the hotel using hotel by
    // name
    // method
    // Next test if its null and
    // whether the
    // address and number of stars is
    // correct
  }

  /**
   * This method make sure the hotel is linked to divesafe by validating hotel numbers in divesafe.
   *
   * @author Ziyue Wang
   */

  @Then("the number of hotels in the system is {int} \\(p13)")
  public void the_number_of_hotels_in_the_system_is_p13(Integer int1) {
    // use.getHotels()to get list of Hotel and it's size
    assertTrue(diveSafe.getHotels().size() == int1);
  }

  /**
   * This method asserts tthe correct error message is raised.
   *
   * @param string supposed error message
   * @author Ziyue Wang, Edwin
   */

  @Then("the following {string} shall be raised \\(p13)")
  public void the_following_shall_be_raised_p13(String string) {
    assertEquals(string, error);
  }

  /**
   * This method asserts that new hotel not successfully added are not in divesafe and old hotel
   * still exists.
   *
   * @param string,string2
   * @author Ziyue Wang, Edwin, Farah Mehzabeen
   */

  @Then("the hotel with {string} shall {string} in the system \\(p13)")
  public void the_hotel_with_shall_in_the_system_p13(String hotelName, String target) {
    if (Hotel.getWithName(hotelName) != null) {
      assertEquals(target, "exist");
    } else {
      assertEquals(target, "not exist");
    }
  }

  /**
   * This methods attemps to update an existing hotel.
   *
   * @param newRating
   * @param newName
   * @param newAddress
   * @param oldName
   * @author Ziyue Wang, Edwin
   */

  @When("the admin attempts to update hotel with {string} in the system to have a {string}, {string}, {string}, and {string} \\(p13)")
  public void the_admin_attempts_to_update_hotel_with_in_the_system_to_have_a_and_p13(
      String oldName, String newName, String newAddress, String newRating, String type) {

    callController(HotelController.updateHotel(oldName, newName, newAddress,
        Integer.parseInt(newRating), type));
  }

  /**
   * This methods asserts updated hotel have corresbonding name, address, rating.
   *
   * @param newAddress
   * @param newName
   * @param newRating
   * @author Ziyue Wang
   */

  @Then("the hotel will be updated to have a {string}, {string}, {string}, and {string} \\(p13)")
  public void the_hotel_will_be_updated_to_have_a_and_p13(String newName, String newAddress,
      String newRating, String newType) {
    Hotel hotel = Hotel.getWithName(newName);
    assertEquals(newAddress, hotel.getAddress());
    assertEquals(Integer.parseInt(newRating), hotel.getRating());
    assertEquals(newType, hotel.getType().toString());
  }

  /**
   * This method asserts hotel info are not changed after unsuccessful update.
   *
   * @param oldAddress
   * @param oldName
   * @param oldRating
   * @author Ziyue Wang
   */

  @Then("the hotel will keep its {string}, {string}, {string}, and {string} \\(p13)")
  public void the_hotel_will_keep_its_and_p13(String oldName, String oldAddress, String oldRating,
      String oldType) {
    Hotel hotel = Hotel.getWithName(oldName);
    assertEquals(oldName, hotel.getName());
    assertEquals(oldAddress, hotel.getAddress());
    assertEquals(Integer.parseInt(oldRating), hotel.getRating());
    assertEquals(oldType, hotel.getType().toString());
  }

  /**
   * This method validates raised error.
   *
   * @param string supposed error
   * @author Ziyue Wang
   */

  @Then("the following error {string} shall be raised \\(p13)")
  public void the_following_error_shall_be_raised_p13(String string) {
    Assertions.assertEquals(string, error);
  }

  /**
   * @Author Ziyue Wang, Farah Mehzabeen
   */
  private void callController(String controllerResult) {
    if (!controllerResult.isEmpty()) {
      error += controllerResult;
    }
  }

}
