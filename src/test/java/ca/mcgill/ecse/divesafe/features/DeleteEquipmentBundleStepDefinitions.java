package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.BundleController;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteEquipmentBundleStepDefinitions {

  private DiveSafe diveSafe;

  /**
   * @author Pierre Masselot
   */
  @Given("the following DiveSafe system exists: \\(p6)")
  public void the_following_dive_safe_system_exists_p6(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    // get values from table
    Date startDate = Date.valueOf(data.get(0).get("startDate"));
    int numDays = Integer.valueOf(data.get(0).get("numDays"));
    int priceOfGuidePerDay = Integer.valueOf(data.get(0).get("priceOfGuidePerDay"));
    // get instance of DiveSafe
    diveSafe = DiveSafeApplication.getDiveSafe();
    // set parameter of the DiveSafe app
    diveSafe.setStartDate(startDate);
    diveSafe.setNumDays(numDays);
    diveSafe.setPriceOfGuidePerDay(priceOfGuidePerDay);
  }

  /**
   * @author Pierre Masselot
   */
  @Given("the following equipment exists in the system: \\(p6)")
  public void the_following_equipment_exists_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    // For each row
    for (var row : data) {
      // get values from table
      String name = row.get("name");
      int weight = Integer.valueOf(row.get("weight"));
      int pricePerDay = Integer.valueOf(row.get("pricePerDay"));
      // add equipment with specified values
      diveSafe.addEquipment(name, weight, pricePerDay);
    }
  }

  /**
   * @author Theodore Glavas
   *
   */
  @Given("the following equipment bundles exist in the system: \\(p6)")
  public void the_following_equipment_bundles_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    // Convert dataTable to List of Maps
    List<Map<String, String>> data = dataTable.asMaps();

    // For each row
    for (var row : data) {
      int discount = Integer.parseInt(row.get("discount"));
      String[] equipmentNames = row.get("items").split(",");
      String[] equipmentQuantities = row.get("quantities").split(",");

      // create bundle and add all bundleItems
      var bundle = new EquipmentBundle(row.get("name"), discount, diveSafe);
      for (int i = 0; i < equipmentNames.length; i++) {
        bundle.addBundleItem(Integer.parseInt(equipmentQuantities[i]), diveSafe,
            (Equipment) Item.getWithName(equipmentNames[i]));
      }
    }
  }

  /**
   * @author Theodore Glavas
   */
  @Given("the following members exist in the system: \\(p6)")
  public void the_following_members_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    // Convert dataTable to List of Maps
    List<Map<String, String>> data = dataTable.asMaps();

    // For each row
    for (var row : data) {
      int numDays = Integer.parseInt(row.get("numDays"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));
      String[] itemBookings = row.get("itemBookings").split(",");
      String[] bookedQuantities = row.get("quantity").split(",");

      // create member and add itemBookings
      var member = new Member(row.get("email"), row.get("password"), row.get("name"),
          row.get("emergencyContact"), numDays, guideRequired, hotelRequired, diveSafe);

      for (int i = 0; i < itemBookings.length; i++) {
        member.addItemBooking(Integer.parseInt(bookedQuantities[i]), diveSafe,
            Item.getWithName(itemBookings[i]));
      }
    }
  }

  /**
   * @author Ari Arabian
   */
  @When("the administrator attempts to delete the equipment bundle {string} \\(p6)")
  public void the_administrator_attempts_to_delete_the_equipment_bundle_p6(String string) {
    BundleController.deleteEquipmentBundle(string);
  }

  /**
   * @author Ari Arabian
   */
  @Then("the number of equipment bundles in the system shall be {string} \\(p6)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p6(String string) {
    // Write code here that turns the phrase above into concrete actions
    assertEquals(Integer.parseInt(string), diveSafe.numberOfBundles());
  }

  /**
   * @author Yash Khapre
   */
  @Then("the equipment bundle {string} shall not exist in the system \\(p6)")
  public void the_equipment_bundle_shall_not_exist_in_the_system_p6(String string) {
    // Item is superclass of EquipmentBundle.

    // Should still pass if item is an equipment or null
    var item = Item.getWithName(string);
    assertTrue(item == null || item instanceof Equipment);
  }

  /**
   * @author Yash Khapre
   */
  @Then("the equipment bundle {string} shall preserve the following properties: \\(p6)")
  public void the_equipment_bundle_shall_preserve_the_following_properties_p6(String string,
      io.cucumber.datatable.DataTable dataTable) {

    // import dataTable as List of Maps
    List<Map<String, String>> data = dataTable.asMaps();

    for (var row : data) {
      // Assume bundle with name string exists, if not exception will tell us why
      var bundle = (EquipmentBundle) Item.getWithName(row.get("name"));

      int discount = Integer.parseInt(row.get("discount"));
      assertEquals(discount, bundle.getDiscount());

      // Adds all the bundle items and their quantities to empty arrays
      String[] expectedItemNames = row.get("items").split(",");
      String[] expectedItemQuantity = row.get("quantities").split(",");

      // Create a Map to contain all the desired (item,quantity) pairs
      Map<String, Integer> expectedItemsAndQuantities = new HashMap<>();

      // Adds integer,string pairs to the map
      for (int i = 0; i < expectedItemNames.length; i++) {
        expectedItemsAndQuantities.put(expectedItemNames[i],
            Integer.parseInt(expectedItemQuantity[i]));
      }

      List<BundleItem> items = bundle.getBundleItems();

      // Create a Map to contain all items in the bundle (item, quantity)
      Map<String, Integer> actualItemsAndQuantities = new HashMap<>();

      // Add bundle item,quantity pairs to the map
      for (var item : items) {
        actualItemsAndQuantities.put(item.getEquipment().getName(), item.getQuantity());
      }

      // Compare maps to ensure that the maps are exactly equal
      assertEquals(expectedItemsAndQuantities, actualItemsAndQuantities);
    }
  }

  /**
   * @author William Lin
   */
  @Then("the member {string} shall have the following bookable items: \\(p6)")
  public void the_member_shall_have_the_following_bookable_items_p6(String string,
      io.cucumber.datatable.DataTable dataTable) {

    // create instance of member by using their email
    var member = Member.getWithEmail(string);
    List<Map<String, String>> data = dataTable.asMaps();
    Map<String, Integer> expectedMemberItemBookings = new HashMap<>();

    // Map the expected booked items for the data table
    for (var row : data) {
      expectedMemberItemBookings.put(row.get("itemBookings"), Integer.parseInt(row.get("quantity")));
    }

    var memberItemBookings = member.getItemBookings();
    Map<String, Integer> actualMemberItemBookings = new HashMap<>();

    // Map the actual member booked items
    for (var bItem : memberItemBookings) {
      actualMemberItemBookings.put(bItem.getItem().getName(), bItem.getQuantity());
    }

    // compare both maps making sure that data list booked items and member's booked items are the
    // same
    assertEquals(expectedMemberItemBookings, actualMemberItemBookings);
  }

  /**
   * @author William Lin
   */
  @Then("the number of pieces of equipment in the system shall be {string} \\(p6)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p6(String string) {
    // verify that there are the correct number of pieces of equipment in the system after
    // attempting deleting.
    assertEquals(Integer.parseInt(string), diveSafe.numberOfEquipments());
  }
}
