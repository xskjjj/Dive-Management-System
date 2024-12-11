package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.EquipmentController;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Item;
// Default imports
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


// Step method definitions
public class AddEquipmentStepDefinitions {

  private DiveSafe diveSafe; // The system instance variable
  private String error; // The current error

  /**
   * Creating the divesafe system with the desired number of days, date and price of guide per
   * day
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param dataTable The table that contains the inputs specified in the feature file
   */
  @Given("the following DiveSafe system exists: \\(p4)")
  public void the_following_dive_safe_system_exists_p4(io.cucumber.datatable.DataTable dataTable) {
    error = "";

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      // Extracting the components of the table
      String startDate = row.get("startDate");
      Date date = Date.valueOf(startDate);
      int numDays = Integer.parseInt(row.get("numDays"));
      int priceOfGuidePerDay = Integer.parseInt(row.get("priceOfGuidePerDay"));

      // Creating the new system
      diveSafe = DiveSafeApplication.getDiveSafe();
      diveSafe.setNumDays(numDays);
      diveSafe.setStartDate(date);
      diveSafe.setPriceOfGuidePerDay(priceOfGuidePerDay);

    }
  }

  /**
   * Adding the pieces of equipment that should already be in the system before going into the when
   * clause
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param dataTable The table that contains the inputs specified in the feature file
   */
  @Given("the following pieces of equipment exist in the system: \\(p4)")
  public void the_following_pieces_of_equipment_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    // Extracting the components of the table and adding the corresponding pieces of equipment
    for (var row : rows) {

      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int pricePerDay = Integer.parseInt(row.get("pricePerDay"));

      diveSafe.addEquipment(name, weight, pricePerDay);

    }
  }

  /**
   * Adding the bundles that should already be in the system before going into the when clause
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param dataTable The table that contains the inputs specified in the feature file
   */
  @Given("the following equipment bundles exist in the system: \\(p4)")
  public void the_following_equipment_bundles_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      // Extracting the components of the table
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      String[] items = row.get("items").split(",");
      String[] quantity = row.get("quantity").split(",");

      // Creating the bundle
      EquipmentBundle bundle = new EquipmentBundle(name, discount, diveSafe);

      // Creating the corresponding bundleItems associated to the previously create equipment items
      for (int j = 0; j < quantity.length; j++) {
        var bundleItem = new BundleItem(Integer.parseInt(quantity[j]), diveSafe, bundle,
            (Equipment) Item.getWithName(items[j]));
        bundle.addBundleItem(bundleItem);
      }

      // Adding the bundle to the system
      diveSafe.addBundle(bundle);

    }
  }

  /**
   * Calls the controller method that adds a piece of equipment to the system
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param name Name of the equipment
   * @param weight Weight of the equipment
   * @param pricePerDay Price per day of the equipment
   */
  @When("the administator attempts to add a new piece of equipment to the system with name {string}, weight {string}, and price per day {string} \\(p4)")
  public void the_administator_attempts_to_add_a_new_piece_of_equipment_to_the_system_with_name_weight_and_price_per_day_p4(
      String name, String weight, String pricePerDay) {

    // Adding the piece of equipment to the system using the controller method
    error = EquipmentController.addEquipment(name, Integer.parseInt(weight), Integer.parseInt(pricePerDay));
  }

  /**
   * Checks that the number of pieces of equipment in the system is as expected
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param number The expected number of pieces of equipment in the system
   */
  @Then("the number of pieces of equipment in the system shall be {string} \\(p4)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p4(String number) {

    // Making sure the number of pieces of equipment in the system is as expected
    assertEquals(Integer.parseInt(number), diveSafe.numberOfEquipments());

  }

  /**
   * Checks that the newly added piece of equipment exists in the system
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param name Expected name of the piece of equipment
   * @param weight Expected weight of the piece of equipment
   * @param pricePerDay Expected price per day of the piece of equipment
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per day {string} shall exist in the system \\(p4)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_day_shall_exist_in_the_system_p4(
      String name, String weight, String pricePerDay) {

    // Extracting the bookable item with the required name
    Item item = Item.getWithName(name);

    // Checking that it's not null, that it's a piece of equipment (not a bundle) and that all its
    // attributes are as required
    assertNotNull(item);
    assertTrue(item instanceof Equipment);
    assertEquals(Integer.parseInt(weight), ((Equipment) item).getWeight());
    assertEquals(Integer.parseInt(pricePerDay), ((Equipment) item).getPricePerDay());

  }

  /**
   * Checks that the following piece of equipment does not exist in the system
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param name Name of the equipment that should not exist
   * @param weight Weight of the equipment that should not exist
   * @param pricePerDay Price per day of the equipment that should not exist
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per day {string} shall not exist in the system \\(p4)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_day_shall_not_exist_in_the_system_p4(
      String name, String weight, String pricePerDay) {

    // Extracting the bookable item with the required name
    Item item = Item.getWithName(name);

    // Checking that it's either null, that it's a bundle, or that one of the attributes do not
    // match
    if (item != null && item instanceof Equipment equipmentItem) {
      assertTrue(
          Integer.parseInt(weight) != equipmentItem.getWeight()
              || Integer.parseInt(pricePerDay) != equipmentItem.getPricePerDay(),
          "The equipment item exists in the system");
    }

  }

  /**
   * Checks that the error thrown is as expected
   *
   * @author Wassim Jabbour, Matthieu Hakim, Karl Rouhana, Tinetendo Makata, Adam Kazma, Ralph
   *         Nassar
   * @param expectedError The expected error
   */
  @Then("the system shall raise the error {string} \\(p4)")
  public void the_system_shall_raise_the_error_p4(String expectedError) {

    // Checking that the error is as expected
    assertTrue(error.contains(expectedError));

  }
}
