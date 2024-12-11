package ca.mcgill.ecse.divesafe.features;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.MemberController;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.ItemBooking;
import ca.mcgill.ecse.divesafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class UpdateMemberStepDefinitions {

  private DiveSafe diveSafe;
  private String error;

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following DiveSafe system exists: \\(p15)")
  public void the_following_dive_safe_system_exists_p15(
      io.cucumber.datatable.DataTable dataTable) {
    this.error = null;

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    this.diveSafe = DiveSafeApplication.getDiveSafe();
    for (Map<String, String> row : rows) {

      Date startDate = java.sql.Date.valueOf(row.get("startDate"));
      int numDays = Integer.parseInt(row.get("numDays"));
      int daylyGuidePrice = Integer.parseInt(row.get("priceOfGuidePerDay"));

      this.diveSafe.setStartDate(startDate);
      this.diveSafe.setNumDays(numDays);
      this.diveSafe.setPriceOfGuidePerDay(daylyGuidePrice);
    }
  }

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following equipment exists in the system: \\(p15)")
  public void the_following_equipment_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int daylyPrice = Integer.parseInt(row.get("pricePerDay"));

      new Equipment(name, weight, daylyPrice, this.diveSafe);
    }
  }

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following equipment bundles exist in the system: \\(p15)")
  public void the_following_equipment_bundles_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      List<Integer> quantity = Arrays.stream(row.get("quantity").split(",")).map(String::trim)
          .mapToInt(Integer::parseInt).boxed().toList();
      List<String> items = Arrays.asList(row.get("items").split(","));

      EquipmentBundle equipmentBundle = new EquipmentBundle(name, discount, this.diveSafe);

      for (int i = 0; i < items.size(); i++) {
        new BundleItem(quantity.get(i), this.diveSafe, equipmentBundle,
            (Equipment) Equipment.getWithName(items.get(i)));
      }
    }
  }

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following members exist in the system: \\(p15)")
  public void the_following_members_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {

      String email = row.get("email");
      String name = row.get("name");
      String password = row.get("password");
      String emergencyContact = row.get("emergencyContact");
      int numDays = Integer.parseInt(row.get("numDays"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));
      List<Integer> requestedQuantities = Arrays.asList(row.get("requestedQuantities").split(","))
          .stream().map(String::trim).mapToInt(Integer::parseInt).boxed().toList();
      List<String> items = Arrays.asList(row.get("items").split(","));

      Member member = new Member(email, password, name, emergencyContact, numDays, guideRequired,
          hotelRequired, this.diveSafe);

      for (int i = 0; i < items.size(); i++) {
        Item item = Item.getWithName(items.get(i));
        member.addItemBooking(requestedQuantities.get(i), this.diveSafe, item);
      }

    }

  }

  /**
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @param newNumDays
   * @param newItems
   * @param newRequestedQuantities
   * @param newGuideRequired
   * @param newHotelRequired
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @When("the member with {string} attempts to update their account with {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p15)")
  public void the_member_with_attempts_to_update_their_account_with_and_p15(String email,
      String newPassword, String newName, String newEmergencyContact, String newNumDays,
      String newItems, String newRequestedQuantities, String newGuideRequired,
      String newHotelRequired) {
    List<String> itemNames = Arrays.asList(newItems.split(","));
    List<Integer> itemQuantities = Arrays.stream(newRequestedQuantities.split(","))
        .map(String::trim).mapToInt(Integer::parseInt).boxed().toList();

    error = MemberController.updateMember(email, newPassword, newName, newEmergencyContact,
        Integer.parseInt(newNumDays), Boolean.parseBoolean(newGuideRequired),
        Boolean.parseBoolean(newHotelRequired), itemNames, itemQuantities);
  }

  /**
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @param newNumDays
   * @param newItems
   * @param newRequestedQuantities
   * @param newGuideRequired
   * @param newHotelRequired
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Then("the member account with {string} shall be updated with {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p15)")
  public void the_member_account_with_shall_be_updated_with_and_p15(String email,
      String newPassword, String newName, String newEmergencyContact, String newNumDays,
      String newItems, String newRequestedQuantities, String newGuideRequired,
      String newHotelRequired) {
    Member member = (Member) Member.getWithEmail(email);
    assertNotNull(member);
    assertEquals(newPassword, member.getPassword());
    assertEquals(newName, member.getName());
    assertEquals(newEmergencyContact, member.getEmergencyContact());
    assertEquals(Integer.parseInt(newNumDays), member.getNumDays());
    assertEquals(Boolean.parseBoolean(newGuideRequired), member.getGuideRequired());
    assertEquals(Boolean.parseBoolean(newHotelRequired), member.getHotelRequired());


    List<Integer> expectedQuantities = Arrays.stream(newRequestedQuantities.split(","))
        .map(String::trim).mapToInt(Integer::parseInt).boxed().toList();
    List<String> expectedItems = Arrays.asList(newItems.split(","));

    // check member booked item size
    List<ItemBooking> memberItems = member.getItemBookings();
    assertEquals(expectedItems.size(), memberItems.size());

    // Check if item assigned to the member exists with the right quantity as specified by the input
    // string
    for (ItemBooking memberItem : memberItems) {
      assertTrue(expectedItems.contains(memberItem.getItem().getName()));
      int index = expectedItems.indexOf(memberItem.getItem().getName());
      assertEquals(expectedQuantities.get(index), (Integer) memberItem.getQuantity());
    }
  }

  /**
   * @param expectedMemberNum expected number of members
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Then("there are {int} members in the system. \\(p15)")
  public void there_are_members_in_the_system_p15(Integer expectedMemberNum) {
    assertEquals(expectedMemberNum, (Integer) this.diveSafe.numberOfMembers());
  }

  /**
   * @param expectedError expected error
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Then("the following {string} shall be raised. \\(p15)")
  public void the_following_shall_be_raised_p15(String expectedError) {
    assertEquals(expectedError, error);
  }
}
