package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.HotelController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Hotel;
import ca.mcgill.ecse.divesafe.model.Hotel.HotelType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



public class DeleteHotelStepDefinitions {

  /**
   * The existing DiveSafe system.
   */
  private DiveSafe diveSafe;

  /**
   * This method is part of the "given" clause that creates a diveSafe object directly using the
   * model, in order to perform testing.
   *
   * @author Samuel Valentine, Onyekachi Ezekwem, Rui Du, Youssof Mohamed Masoud.
   * @param dataTable : which includes the DiveSafe object's attributes (i.e. StartDate, NumDays,
   *        and priceOfQuidePerDay).
   */
  @Given("the following DiveSafe system exists: \\(p16)")
  public void the_following_dive_safe_system_exists_p16(io.cucumber.datatable.DataTable dataTable) {

    diveSafe = DiveSafeApplication.getDiveSafe();

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      diveSafe.setStartDate(Date.valueOf(columns.get("startDate")));
      diveSafe.setNumDays((Integer.parseInt(columns.get("numDays"))));
      diveSafe.setPriceOfGuidePerDay((Integer.parseInt(columns.get("priceOfGuidePerDay"))));
    }
  }

  /**
   * This method is part of the "given" clause that creates and adds hotel objects directly using
   * the model, in order to perform testing.
   *
   * @author Samuel Valentine, Onyekachi Ezekwem, Rui Du, Youssof Mohamed Masoud.
   * @param dataTable : which represents the hotels by including their names, addresses, and
   *        ratings.
   */
  @Given("the following hotels exist in the system: \\(p16)")
  public void the_following_hotels_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> columns : rows) {
      HotelType type = HotelType.valueOf(columns.get("type"));
      diveSafe.addHotel(columns.get("name"), columns.get("address"),
          Integer.parseInt(columns.get("rating")), type);
    }
  }

  /**
   * This method is part of the "when" clause that uses the controller to attempt and delete a
   * hotel.
   *
   * @author Samuel Valentine, Onyekachi Ezekwem, Rui Du, Youssof Mohamed Masoud.
   * @param string : which represents the name of the hotel.
   */
  @When("the administator attempts to delete the hotel in the system with name {string} \\(p16)")
  public void the_administator_attempts_to_delete_the_hotel_in_the_system_with_name_p16(
      String name) {

    HotelController.deleteHotel(name);
  }

  /**
   * This method is part of the "then" clause that checks the result of the attempting to delete the
   * hotel via the controller.
   *
   * @author Samuel Valentine, Onyekachi Ezekwem, Rui Du, Youssof Mohamed Masoud, Yakir Bender.
   * @param string : which represents the number of hotels that will remain in the system after
   *        deletion.
   */
  @Then("the number of hotels in the system shall be {string} \\(p16)")
  public void the_number_of_hotels_in_the_system_shall_be_p16(String string) {

    assertEquals(Integer.parseInt(string), diveSafe.getHotels().size());
  }

  /**
   * This method is part of the "then" clause that checks the result of the attempting to delete the
   * hotel via the controller.
   *
   * @author Samuel Valentine, Onyekachi Ezekwem, Rui Du, Youssof Mohamed Masoud, Yakir Bender.
   * @param hotelName, hotelAddress, hotelRating : which represents the name, address, and rating of
   *        the hotel that will no longer exist in the system.
   */
  @Then("the hotel with name {string}, address {string}, rating {string}, and type {string} shall not exist in the system \\(p16)")
  public void the_hotel_with_name_address_and_rating_shall_not_exist_in_the_system_p16(
      String hotelName, String hotelAddress, String hotelRating, String type) {

    assertNull(Hotel.getWithName(hotelName));

  }

  /**
   * This method is part of the "then" clause that checks the result of the attempting to delete the
   * hotel via the controller.
   *
   * @author Samuel Valentine, Onyekachi Ezekwem, Rui Du, Youssof Mohamed Masoud, Yakir Bender.
   * @param datatable : which represents the hotels that will remain in the system after deletion.
   */
  @Then("the following hotels shall exist in the system: \\(p16)")
  public void the_following_hotels_shall_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> columns : rows) {
      assertTrue(Hotel.hasWithName(columns.get("name")));
      assertEquals(columns.get("address"), Hotel.getWithName(columns.get("name")).getAddress());
      assertEquals(Integer.parseInt(columns.get("rating")),
          Hotel.getWithName(columns.get("name")).getRating());
    }
  }

}
