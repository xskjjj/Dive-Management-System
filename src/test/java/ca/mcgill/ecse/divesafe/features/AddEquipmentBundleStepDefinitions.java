package ca.mcgill.ecse.divesafe.features;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.BundleController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddEquipmentBundleStepDefinitions {

  private DiveSafe diveSafe;
  private String error = "";

  // @author everyone
  @Given("the following DiveSafe system exists: \\(p2)")
  public void the_following_dive_safe_system_exists_p2(io.cucumber.datatable.DataTable dataTable) {
    diveSafe = DiveSafeApplication.getDiveSafe();
    error = "";
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      diveSafe.setStartDate(Date.valueOf(row.get("startDate")));
      diveSafe.setNumDays(Integer.parseInt(row.get("numDays")));
      diveSafe.setPriceOfGuidePerDay(Integer.parseInt(row.get("priceOfGuidePerDay")));
    }
  }

  // @author Danny Tu
  @Given("the following equipment exists in the system: \\(p2)")
  public void the_following_equipment_exists_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int pricePerDay = Integer.parseInt(row.get("pricePerDay"));
      diveSafe.addEquipment(name, weight, pricePerDay);
    }
  }

  // @author Salim Benchekroun
  @When("the administrator attempts to add an equipment bundle with name {string}, discount {string}, items {string}, and quantities {string} \\(p2)")
  public void the_administrator_attempts_to_add_an_equipment_bundle_with_name_discount_items_and_quantities_p2(
      String name, String discountString, String items, String quantities) {
    int discount = Integer.parseInt(discountString);
    List<Integer> quantityList = new ArrayList<Integer>();
    List<String> itemList = new ArrayList<String>();
    if (!items.isEmpty() && !quantities.isEmpty()) {
      itemList = Arrays.asList(items.split(","));
      for (String quantity : quantities.split(",")) {
        quantityList.add(Integer.parseInt(quantity));
      }
    }
    error = BundleController.addEquipmentBundle(name, discount, itemList, quantityList);
  }

  // @author Arturo Mory Ramirez
  @Then("the number of equipment bundles in the system shall be {string} \\(p2)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p2(String string) {
    assertEquals(Integer.parseInt(string), diveSafe.numberOfBundles());
  }

  // @author Jian Long (Noah) Ye
  @Then("the equipment bundle {string} shall exist in the system \\(p2)")
  public void the_equipment_bundle_shall_exist_in_the_system_p2(String string) {
    assertNotNull(EquipmentBundle.getWithName(string));
  }

  // @author Runge (Karen) Fu
  @Then("the equipment bundle {string} shall contain the items {string} with quantities {string} \\(p2)")
  public void the_equipment_bundle_shall_contain_the_items_with_quantities_p2(String name,
      String items, String quantities) {
    var bundle = EquipmentBundle.getWithName(name);
    List<String> itemList = new ArrayList<String>();
    List<Integer> quantityList = new ArrayList<Integer>();
    if (!items.isEmpty() && !quantities.isEmpty()) {
      itemList = Arrays.asList(items.split(","));
      for (String quantity : quantities.split(",")) {
        quantityList.add(Integer.parseInt(quantity));
      }
    }
    for (int i = 0; i < itemList.size(); i++) {
      var item = bundle.getBundleItems().get(i);
      assertEquals(itemList.get(i), item.getEquipment().getName());
      assertEquals(quantityList.get(i), item.getQuantity());
    }
  }

  // @author everyone
  @Then("the equipment bundle {string} shall have a discount of {string} \\(p2)")
  public void the_equipment_bundle_shall_have_a_discount_of_p2(String name, String discount) {
    var bundle = EquipmentBundle.getWithName(name);
    assertEquals(Integer.parseInt(discount), bundle.getDiscount());
  }

  // @author Peini Cheng
  @Then("the error {string} shall be raised \\(p2)")
  public void the_error_shall_be_raised_p2(String string) {
    assertTrue(error.startsWith(string));
  }

  // @author Peini Cheng
  @Given("the following equipment bundles exist in the system: \\(p2)")
  public void the_following_equipment_bundles_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      var bundle = diveSafe.addBundle(row.get("name"), Integer.parseInt(row.get("discount")));
      List<String> items = Arrays.asList(row.get("items").split(","));
      List<String> quantities = Arrays.asList(row.get("quantities").split(","));
      for (int i = 0; i < items.size(); i++) {
        var equipment = Equipment.getWithName(items.get(i));
        if (equipment != null) {
          bundle.addBundleItem(Integer.parseInt(quantities.get(i)), diveSafe, equipment);
        }
      }
    }
  }

}
