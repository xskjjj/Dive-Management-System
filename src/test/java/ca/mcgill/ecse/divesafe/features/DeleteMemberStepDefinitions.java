package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.MemberController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteMemberStepDefinitions {
  private DiveSafe diveSafe;

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the diveSafe numDays, startDate, and priceOfGuidePerDay
   *        attributes
   */
  @Given("the following DiveSafe system exists: \\(p14)")
  public void the_following_dive_safe_system_exists_p14(
      io.cucumber.datatable.DataTable dataTable) {
    diveSafe = DiveSafeApplication.getDiveSafe();

    List<Map<String, String>> theDataTable = dataTable.asMaps(String.class, String.class);

    diveSafe.setNumDays(Integer.parseInt(theDataTable.get(0).get("numDays")));
    diveSafe.setStartDate(Date.valueOf(theDataTable.get(0).get("startDate")));
    diveSafe.setPriceOfGuidePerDay(Integer.parseInt(theDataTable.get(0).get("priceOfGuidePerDay")));
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable table containing the equipment that we need to instantiate in our system
   */
  @Given("the following equipment exists in the system: \\(p14)")
  public void the_following_equipment_exists_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> cur : rows) {
      diveSafe.addEquipment(cur.get("name"), Integer.parseInt(cur.get("weight")),
          Integer.parseInt(cur.get("pricePerDay")));
    }

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the equipmentBundles that we need to instantiate in our
   *        system
   */
  @Given("the following equipment bundles exist in the system: \\(p14)")
  public void the_following_equipment_bundles_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> equipmentBundles = dataTable.asMaps();
    for (Map<String, String> e : equipmentBundles) {
      diveSafe.addBundle(e.get("name"), Integer.parseInt(e.get("discount")));

    }
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the Members that we need to instantiate in our system
   */
  @Given("the following members exist in the system: \\(p14)")
  public void the_following_members_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> columns : rows) {

      Member m =
          diveSafe.addMember(columns.get("email"), columns.get("password"), columns.get("name"),
              columns.get("emergencyContact"), Integer.parseInt(columns.get("numDays")),
              Boolean.parseBoolean(columns.get("guideRequired")),
              Boolean.parseBoolean(columns.get("hotelRequired")));
      String[] itemStrings = columns.get("items").split(",");
      String[] itemQuantity = columns.get("requestedQuantities").split(",");
      for (int i = 0; i < itemStrings.length; i++) {
        m.addItemBooking(Integer.parseInt(itemQuantity[i]), diveSafe,
            Item.getWithName(itemStrings[i]));
      }
    }

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the Guides that we need to instantiate in our system
   */
  @Given("the following guides exist in the system: \\(p14)")
  public void the_following_guides_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    for (Map<String, String> r : data) {

      diveSafe.addGuide(r.get("email"), r.get("password"), r.get("name"),
          r.get("emergencyContact"));
    }
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the account that the member is trying to delete
   */
  @When("the member attempts to delete the account with email {string} \\(p14)")
  public void the_member_attempts_to_delete_the_account_with_email_p14(String string) {
    MemberController.deleteMember(string);

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the Member that was hopefully deleted
   */
  @Then("the member account with the email {string} does not exist \\(p14)")
  public void the_member_account_with_the_email_does_not_exist_p14(String string) {
    assertNull(Member.getWithEmail(string));
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the number of members that should now be in the system
   */
  @Then("there are {string} members in the system. \\(p14)")
  public void there_are_members_in_the_system_p14(String string) {
    assertEquals(Integer.parseInt(string), diveSafe.getMembers().size());
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the Member account that the member is trying to delete
   */
  @When("the member attempts to delete the member account with email {string} \\(p14)")
  public void the_member_attempts_to_delete_the_member_account_with_email_p14(String string) {
    MemberController.deleteMember(string);
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the guide account that should still exist in the system
   */
  @Then("the guide account linked to the {string} shall exist in the system \\(p14)")
  public void the_guide_account_linked_to_the_shall_exist_in_the_system_p14(String string) {
    assertNotNull(Guide.getWithEmail(string));
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param numGuides the number of Guides that should remain in the system
   */
  @Then("the number of guides in the system is {int} \\(p14)")
  public void the_number_of_guides_in_the_system_is_p14(Integer numGuides) {
    assertEquals(numGuides, Integer.valueOf(diveSafe.numberOfGuides()));
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param numMembers the number of Members that should still be in the systme
   */
  @Then("there are {int} members in the system. \\(p14)")
  public void there_are_members_in_the_system_p14(Integer numMembers) {
    assertEquals(numMembers, Integer.valueOf(diveSafe.numberOfMembers()));
  }

}
