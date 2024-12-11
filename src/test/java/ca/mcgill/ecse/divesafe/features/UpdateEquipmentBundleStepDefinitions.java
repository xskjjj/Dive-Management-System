package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.BundleController;

import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateEquipmentBundleStepDefinitions {

  private DiveSafe diveSafe;
  private String error;

  // @Joey
  @Given("the following DiveSafe system exists: \\(p8)")
  public void the_following_dive_safe_system_exists_p8(io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> diveSafe1 = dataTable.asMaps(String.class, String.class);
    var date = diveSafe1.get(0).get("startDate");
    var days = diveSafe1.get(0).get("numDays");
    var price = diveSafe1.get(0).get("priceOfGuidePerDay");
    error = "";
    diveSafe = DiveSafeApplication.getDiveSafe();
    diveSafe.setStartDate(java.sql.Date.valueOf(date));
    diveSafe.setNumDays(Integer.parseInt(days));
    diveSafe.setPriceOfGuidePerDay(Integer.parseInt(price));
  }

  // @Joey
  @Given("the following equipment exists in the system: \\(p8)")
  public void the_following_equipment_exists_in_the_system_p8(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> equipmentInfo = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> equipment : equipmentInfo) {
      var name = equipment.get("name");
      var weight = equipment.get("weight");
      var pricePerDay = equipment.get("pricePerDay");
      new Equipment(name, Integer.parseInt(weight), Integer.parseInt(pricePerDay), diveSafe);
    }
  }

  // @Aigiarn
  @Given("the following equipment bundles exist in the system: \\(p8)")
  public void the_following_equipment_bundles_exist_in_the_system_p8(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> equipmentBundleInfo = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> equipmentBundle : equipmentBundleInfo) {
      var name = equipmentBundle.get("name");
      var discount = equipmentBundle.get("discount");
      List<String> equipmentsInBundle = Arrays.asList(equipmentBundle.get("items").split(","));
      List<String> equipmentQuantity = Arrays.asList(equipmentBundle.get("quantities").split(","));
      EquipmentBundle equipmentBundle1 =
          new EquipmentBundle(name, Integer.parseInt(discount), diveSafe);

      for (int i = 0; i < equipmentsInBundle.size(); i++) {
        new BundleItem(Integer.parseInt(equipmentQuantity.get(i)), diveSafe, equipmentBundle1,
            (Equipment) Item.getWithName(equipmentsInBundle.get(i)));
      }
    }
  }

  // @Maya
  @When("the administrator attempts to update the equipment bundle {string} to have name {string}, discount {string}, items {string}, and quantities {string} \\(p8)")
  public void the_administrator_attempts_to_update_the_equipment_bundle_to_have_name_discount_items_and_quantities_p8(
      String bundleName, String newBundleName, String discountString, String itemsInBundleString,
      String quantityOfItemsString) {
    List<String> newEquipmentNames = Arrays.asList(itemsInBundleString.split(","));
    List<String> newEquipmentQuantities = Arrays.asList(quantityOfItemsString.split(","));
    List<Integer> newEquipmentQuantInt = new ArrayList<Integer>();

    for (String s : newEquipmentQuantities) {
      if (!s.equals("")) {
        newEquipmentQuantInt.add(Integer.valueOf(s));
      }
    }

    error += BundleController.updateEquipmentBundle(bundleName, newBundleName,
        Integer.parseInt(discountString), newEquipmentNames, newEquipmentQuantInt);
  }

  // @Maya
  @Then("the number of equipment bundles in the system shall be {string} \\(p8)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p8(
      String nrOfEquipmentBundle) {
    assertEquals(Integer.parseInt(nrOfEquipmentBundle), diveSafe.numberOfBundles());
  }

  // @Aigiarn, Joey, Ke
  @Then("the equipment bundle {string} shall contain the items {string} with quantities {string} \\(p8)")
  public void the_equipment_bundle_shall_contain_the_items_with_quantities_p8(String bundleName,
      String itemNames, String quantityStrings) {

    List<String> itemNamesCleaned = Arrays.asList(itemNames.split(","));
    List<String> quantityStringsCleaned = Arrays.asList(quantityStrings.split(","));
    EquipmentBundle equipmentBundle = null;
    List<EquipmentBundle> equipmentBundleList = diveSafe.getBundles();

    for (EquipmentBundle temp : equipmentBundleList) {
      if (temp.getName().equals(bundleName)) {
        equipmentBundle = temp;
        break;
      }
    }

    assertNotNull(equipmentBundle);
    
     // check bundle size
     assertEquals(itemNamesCleaned.size(), equipmentBundle.getBundleItems().size());

    for (int i = 0; i < itemNamesCleaned.size(); i++) {
      BundleItem currentItem = null;
      int quantity = Integer.parseInt(quantityStringsCleaned.get(i));
      for (BundleItem temp2 : equipmentBundle.getBundleItems()) {
        if (temp2.getEquipment().getName().equals(itemNamesCleaned.get(i))) {
          currentItem = temp2;
          break;
        }
      }
      assertNotNull(currentItem);
      assertEquals(quantity, currentItem.getQuantity());
    }

  }

  // @Ke
  @Then("the equipment bundle {string} shall have a discount of {string} \\(p8)")
  public void the_equipment_bundle_shall_have_a_discount_of_p8(String bundleName,
      String discountString) {

    int discount = Integer.parseInt(discountString);
    EquipmentBundle equipmentBundle = null;
    List<EquipmentBundle> equipmentBundleList = diveSafe.getBundles();

    for (EquipmentBundle temp : equipmentBundleList) {
      if (temp.getName().equals(bundleName)) {
        equipmentBundle = temp;
      }
    }
    assertNotNull(equipmentBundle);
    assertEquals(discount, equipmentBundle.getDiscount());
  }

  // @Mihail
  @Then("the equipment bundle {string} shall not exist in the system \\(p8)")
  public void the_equipment_bundle_shall_not_exist_in_the_system_p8(String bundleName) {

    List<EquipmentBundle> equipmentBundleList = diveSafe.getBundles();

    for (EquipmentBundle temp : equipmentBundleList) {
      if (temp.getName().equals(bundleName)) {
        fail();
      }
    }
  }

  // @Ke
  @Then("the number of pieces of equipment in the system shall be {string} \\(p8)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p8(String totalEquipment) {
    assertEquals(Integer.parseInt(totalEquipment), diveSafe.numberOfEquipments());
  }

  // @Selina
  @Then("the error {string} shall be raised \\(p8)")
  public void the_error_shall_be_raised_p8(String string) {
    assertTrue(error.contains(string));
  }

}

