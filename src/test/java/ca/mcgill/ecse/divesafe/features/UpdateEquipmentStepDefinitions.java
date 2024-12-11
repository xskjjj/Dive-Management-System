package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.EquipmentController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Item;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateEquipmentStepDefinitions {
  private DiveSafe diveSafe;
  private String error;

  /**
   * @author Asma Gandour
   */
  @Given("the following DiveSafe system exists: \\(p1)")
  public void the_following_dive_safe_system_exists_p1(io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();

    for (var row : rows) {
      diveSafe = DiveSafeApplication.getDiveSafe();
      diveSafe.setStartDate(Date.valueOf(row.get("startDate")));
      diveSafe.setNumDays(Integer.parseInt(row.get("numDays")));
      diveSafe.setPriceOfGuidePerDay(Integer.parseInt(row.get("priceOfGuidePerDay")));
    }
    error = "";
  }

  /**
   * @author Alexandre Chiasera
   */
  @Given("the following pieces of equipment exist in the system: \\(p1)")
  public void the_following_pieces_of_equipment_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();

    for (var row : rows) {
      diveSafe.addEquipment(row.get("name"), Integer.parseInt(row.get("weight")),
          Integer.parseInt(row.get("pricePerDay")));
    }
  }

  /**
   *
   * @author Haroun Guessous
   */
  @Given("the following equipment bundles exist in the system: \\(p1)")
  public void the_following_equipment_bundles_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();

    for (var row : rows) {
      String nameBundle = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      String bundleItems = row.get("items");
      String bundleItemQuantities = row.get("quantity");
      // create empty Bundle
      var newBundle = new EquipmentBundle(nameBundle, discount, diveSafe);

      List<Integer> quantities = new ArrayList<Integer>();
      List<Item> items = new ArrayList<Item>();

      for (var item : Arrays.asList(bundleItems.split(","))) {
        var existingItem = Equipment.getWithName(item);
        items.add(existingItem);
      }

      for (var itemsQuantity : Arrays.asList(bundleItemQuantities.split(","))) {
        var itemQuantity = Integer.parseInt(itemsQuantity);
        quantities.add(itemQuantity);
      }

      for (var equipment : diveSafe.getEquipments()) {
        for (int i = 0; i < items.size(); i++) {
          var existingEquipmentName = equipment.getName();
          var addedItemName = items.get(i).getName();
          var itemQuantity = quantities.get(i);
          if (existingEquipmentName.equals(addedItemName)) {
            // add already existing pieces of equipment to empty bundle
            newBundle.addBundleItem(itemQuantity, diveSafe, equipment);
          }
        }
      }
    }
  }


  /**
   * @author Asma Gandour
   */
  @When("the administator attempts to update the piece of equipment in the system with name {string} to have name {string}, weight {string}, and price per day {string} \\(p1)")
  public void the_administator_attempts_to_update_the_piece_of_equipment_in_the_system_with_name_to_have_name_weight_and_price_per_day_p1(
      String oldName, String newName, String newWeight, String newPricePerDay) {
    callController(EquipmentController.updateEquipment(oldName, newName, Integer.parseInt(newWeight),
        Integer.parseInt(newPricePerDay)));
  }

  /**
   *
   * @author Haroun Guessous
   */
  @Then("the number of pieces of equipment in the system shall be {string} \\(p1)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p1(String string) {
    assertEquals(Integer.parseInt(string), diveSafe.getEquipments().size());
  }

  /**
   *
   * @author Alexandre Chiasera
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per day {string} shall not exist in the system \\(p1)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_day_shall_not_exist_in_the_system_p1(
      String oldName, String oldWeight, String oldPricePerDay) {
    Integer weight = Integer.parseInt(oldWeight);
    Integer pricePerDay = Integer.parseInt(oldPricePerDay);
    // for each piece of equipment in the diveSafe system (for the admin)
    for (Equipment equipment : diveSafe.getEquipments()) {
      if (equipment.getName().equals(oldName) && equipment.getWeight() == weight
          && equipment.getPricePerDay() == pricePerDay) {
        // if everything matches, it means the object is not updated correctly
        fail("The piece of equipment <" + equipment.getName()
            + "> has not been correctly updated by the system");
      }
    }
  }

  /**
   *
   * @author Alexandre Chiasera
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per day {string} shall exist in the system \\(p1)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_day_shall_exist_in_the_system_p1(
      String newName, String newWeight, String newPricePerDay) {
    String name = newName;
    Integer weight = Integer.parseInt(newWeight);
    Integer pricePerDay = Integer.parseInt(newPricePerDay);

    var equipment = (Equipment) Equipment.getWithName(name);
    // for each piece of equipment in the diveSafe system (for the admin)
    assertTrue(equipment.getName().equals(name) && equipment.getWeight() == weight
        && equipment.getPricePerDay() == pricePerDay);
  }

  /**
   * @author Atreyi Srivastava
   */
  @Then("the following pieces of equipment shall exist in the system: \\(p1)")
  public void the_following_pieces_of_equipment_shall_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int pricePerDay = Integer.parseInt(row.get("pricePerDay"));

      var equipment = (Equipment) Item.getWithName(name);
      assertTrue(equipment.getName().equals(name) && equipment.getWeight() == weight
          && equipment.getPricePerDay() == pricePerDay);
    }
  }

  /**
   * @author Mohammad Shaheer Bilal
   */
  @Then("the system shall raise the error {string} \\(p1)")
  public void the_system_shall_raise_the_error_p1(String string) {
    assertEquals(string, error);
  }

  /**
   *
   * @author Haroun Guessous
   */
  @Then("the number of equipment bundles in the system shall be {string} \\(p1)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p1(String string) {
    assertEquals(Integer.parseInt(string), diveSafe.getBundles().size());
  }

  /**
   * @author Atreyi Srivastava
   */
  @Then("the following equipment bundles shall exist in the system: \\(p1)")
  public void the_following_equipment_bundles_shall_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();
    for (var row : rows) {
      String nameBundle = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));

      var bundle = (EquipmentBundle) Item.getWithName(nameBundle);
      assertTrue(bundle.getName().equals(nameBundle) && bundle.getDiscount() == discount);
    }
  }

  private void callController(String controllerResult) {
    if (!controllerResult.isEmpty()) {
      error += controllerResult;
    }
  }

}
