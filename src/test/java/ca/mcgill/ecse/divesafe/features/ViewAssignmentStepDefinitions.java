package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.AssignmentController;
import ca.mcgill.ecse.divesafe.controller.TOAssignment;
import ca.mcgill.ecse.divesafe.model.Assignment;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Hotel;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.Member;
import ca.mcgill.ecse.divesafe.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Implementation of Step Definitions assigned to ECSE 223 Group 5
 *
 * @author Harrison Wang, Jimmy Sheng, Michael Grieco, Yida Pan, Annie Kang, Sibo Huang
 *
 */
public class ViewAssignmentStepDefinitions {

  private DiveSafe diveSafe;
  private List<TOAssignment> assignments;

  /**
   * Instantiates <code>DiveSafe</code> instance for use with Gherkin Scenario.
   *
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following DiveSafe system exists: \\(p5)")
  public void the_following_dive_safe_system_exists_p5(io.cucumber.datatable.DataTable dataTable) {
    /*
     * Get the data for the DiveSafe instance from the Cucumber Feature file. Note: it is assumed
     * that there will only be one element in the list returned from dataTable.asMaps(). This is
     * because there can be only one instance of DiveSafe in the system at any given time. For this
     * reason, we simply grab the first element in the list.
     */
    Map<String, String> diveSafeDataFromCucumber = dataTable.asMaps().get(0);

    Date startDate = Date.valueOf(diveSafeDataFromCucumber.get("startDate"));
    int numDays = Integer.valueOf(diveSafeDataFromCucumber.get("numDays"));
    int priceOfGuidePerDay = Integer.valueOf(diveSafeDataFromCucumber.get("priceOfGuidePerDay"));

    diveSafe = DiveSafeApplication.getDiveSafe();
    diveSafe.setStartDate(startDate);
    diveSafe.setNumDays(numDays);
    diveSafe.setPriceOfGuidePerDay(priceOfGuidePerDay);

    assignments = null;
  }

  /**
   * Step definition to add hotels to the DiveSafe system
   *
   * @param dataTable table containing hotel information
   * @author Michael Grieco
   */
  @Given("the following hotels exist in the system: \\(p5)")
  public void the_following_hotels_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    // convert data table to a map
    List<Map<String, String>> hotelDataList = dataTable.asMaps();

    for (Map<String, String> hotelData : hotelDataList) {
      // get data from entry
      String nameString = hotelData.get("name");
      String addressString = hotelData.get("address");
      int rating = Integer.parseInt(hotelData.get("rating"));

      // add Hotel with parsed data
      diveSafe.addHotel(nameString, addressString, rating, null);
    }
  }

  /**
   * @param dataTable
   * @author YidaPan
   *
   */
  @Given("the following guides exist in the system: \\(p5)")
  public void the_following_guides_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencycontact = row.get("emergencyContact");
      diveSafe.addGuide(email, password, name, emergencycontact);
    }
  }

  /**
   *
   * @param dataTable
   * @author Annie Kang October 20th, 2021
   */
  @Given("the following equipment exists in the system: \\(p5)")
  public void the_following_equipment_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (Map<String, String> row : cucumberData) {
      diveSafe.addEquipment(row.get("name"), Integer.parseInt(row.get("weight")),
          Integer.parseInt(row.get("pricePerDay")));
    }
  }

  /**
   * @author SiboHuang
   * @param dataTable
   */
  @Given("the following equipment bundles exist in the system: \\(p5)")
  public void the_following_equipment_bundles_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (var equipmentBundle : cucumberData) {
      int discount = Integer.valueOf(equipmentBundle.get("discount"));
      String items = equipmentBundle.get("items");
      String quantities = equipmentBundle.get("quantities");
      String[] equipmentItems = items.split(",");
      String[] equipmentQuantities = quantities.split(",");

      var newBundle = new EquipmentBundle(equipmentBundle.get("name"), discount, diveSafe);

      for (int i = 0; i < equipmentItems.length; i++) {
        newBundle.addBundleItem(Integer.valueOf(equipmentQuantities[i]), diveSafe,
            (Equipment) Item.getWithName(equipmentItems[i]));
      }
    }
  }

  /**
   * Run code to simulate the administrator viewing assignments.
   *
   * @author Harrison Wang
   */
  @When("the administrator attempts to view the assignments \\(p5)")
  public void the_administrator_attempts_to_view_the_assignments_p5() {
    assignments = AssignmentController.getAssignments();
  }

  /**
   * Check if number of assignments is equal to the expected value.
   *
   * @author Jimmy Sheng
   * @param string
   */
  @Then("the number of assignments displayed shall be {string} \\(p5)")
  public void the_number_of_assignments_displayed_shall_be_p5(String string) {
    assertEquals(Integer.valueOf(string), assignments.size());
  }

  /**
   * @author Jimmy Sheng
   * @param dataTable
   */
  @Given("the following members exist in the system: \\(p5)")
  public void the_following_members_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    // add each member
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      int numDays = Integer.parseInt(row.get("numDays"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));
      // add the member to DiveSafe
      var member = diveSafe.addMember(email, password, name, emergencyContact, numDays,
          guideRequired, hotelRequired);
      /*
       * Parse items and quantities. The null check is necessary in case a member doesn't wish to
       * rent any equipment (e.g. Mohamed Farah in ViewAssignment.feature, line 53)
       */
      String itemBookingsList = row.get("itemBookings");
      String bookedQtyList = row.get("itemBookingQuantities");
      String[] itemNames = itemBookingsList != null ? itemBookingsList.split(",") : new String[] {};
      String[] itemQuantities = bookedQtyList != null ? bookedQtyList.split(",") : new String[] {};
      // add items and quantities (assume they're the same length)
      for (int i = 0; i < itemNames.length; i++) {
        var itemBooking = Item.getWithName(itemNames[i]);
        int qty = Integer.parseInt(itemQuantities[i]);
        diveSafe.addItemBooking(qty, member, itemBooking);
      }
    }
  }

  /**
   * Instantiates <code>Assignment</code> instances for use with Gherkin Scenario.
   *
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following assignments exist in the system: \\(p5)")
  public void the_following_assignments_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (Map<String, String> assignmentData : cucumberData) {
      var assignmentMember = (Member) User.getWithEmail(assignmentData.get("memberEmail"));
      var assignmentGuide = (Guide) User.getWithEmail(assignmentData.get("guideEmail"));
      var assignmentHotel = Hotel.getWithName(assignmentData.get("hotelName"));
      int startDay = Integer.valueOf(assignmentData.get("startDay"));
      int endDay = Integer.valueOf(assignmentData.get("endDay"));

      Assignment newAssignment = diveSafe.addAssignment(startDay, endDay, assignmentMember);
      newAssignment.setGuide(assignmentGuide);
      newAssignment.setHotel(assignmentHotel);
    }
  }

  /**
   *
   * @param dataTable
   * @author Michael Grieco
   */
  @Then("the following assignment information shall be displayed: \\(p5)")
  public void the_following_assignment_information_shall_be_displayed_p5(
      io.cucumber.datatable.DataTable dataTable) {
    // convert data table to a map
    List<Map<String, String>> outputDataList = dataTable.asMaps();

    for (int i = 0; i < outputDataList.size(); i++) {
      TOAssignment assignment = assignments.get(i);
      Map<String, String> assignmentData = outputDataList.get(i);

      assertStringEqualsIgnoreNull(assignmentData.get("memberEmail"), assignment.getMemberEmail());
      assertStringEqualsIgnoreNull(assignmentData.get("memberName"), assignment.getMemberName());
      assertStringEqualsIgnoreNull(assignmentData.get("guideEmail"), assignment.getGuideEmail());
      assertStringEqualsIgnoreNull(assignmentData.get("guideName"), assignment.getGuideName());
      assertStringEqualsIgnoreNull(assignmentData.get("hotelName"), assignment.getHotelName());
      assertEquals(Integer.parseInt(assignmentData.get("startDay")), assignment.getStartDay());
      assertEquals(Integer.parseInt(assignmentData.get("endDay")), assignment.getEndDay());
      assertEquals(Integer.parseInt(assignmentData.get("totalCostForEquipment")),
          assignment.getTotalCostForEquipment());
      assertEquals(Integer.parseInt(assignmentData.get("totalCostForGuide")),
          assignment.getTotalCostForGuide());
    }
  }

  /** Asserts that the strings are either equal, or one is empty and the other is null, or both are null. */
  private static void assertStringEqualsIgnoreNull(String expected, String actual) {
    if (expected == null && actual == null) {
      return;
    }
    if (((expected == null && actual.isEmpty()) || (actual == null && expected.isEmpty()))) {
      return;
    }
    assertEquals(expected, actual);
  }

}

