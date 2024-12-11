package ca.mcgill.ecse.divesafe.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.GuideController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteGuideStepDefinitions {

  private DiveSafe diveSafe;

  /**
   * Method containing the steps to be executed when setting up the divesafe system instance for
   * the guide deletion tests
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param dataTable Datatable specifying the information with which to setup the divesafe system
   *        instance
   */
  @Given("the following DiveSafe system exists: \\(p12)")
  public void the_following_dive_safe_system_exists_p12(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> exDiveSafe = dataTable.asMaps(String.class, String.class);
    diveSafe = DiveSafeApplication.getDiveSafe();

    diveSafe.setStartDate(Date.valueOf(exDiveSafe.get(0).get("startDate")));
    diveSafe.setNumDays(Integer.parseInt(exDiveSafe.get(0).get("numDays")));
    diveSafe
        .setPriceOfGuidePerDay(Integer.parseInt(exDiveSafe.get(0).get("priceOfGuidePerDay")));
  }

  /**
   * Method used to instantiate the guides in the divesafe system used in the gherkin test for
   * guide deletion
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param dataTable Datatable specifying the guides to be added to the divesafe system instance
   */
  @Given("the following guides exist in the system: \\(p12)")
  public void the_following_guides_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> guideList = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> g : guideList) {
      diveSafe.addGuide(g.get("email"), g.get("password"), g.get("name"),
          g.get("emergencyContact"));
    }
  }

  /**
   * Method used to instantiate the members in the divesafe system used in the gherkin test for
   * guide deletion
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param dataTable Datatable specifying the members to be added to the divesafe system instance
   */
  @Given("the following members exist in the system: \\(p12)")
  public void the_following_members_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> memberList = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> m : memberList) {
      diveSafe.addMember(m.get("email"), m.get("password"), m.get("name"),
          m.get("emergencyContact"), Integer.parseInt(m.get("numDays")),
          Boolean.parseBoolean(m.get("guideRequired")),
          Boolean.parseBoolean(m.get("hotelRequired")));
    }
  }

  /**
   * Method called by the gherkin guide deletion test to interact with the controller api under
   * test. The method deletes a specified guide through a specific controller method
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Email of the guide to be deleted
   */
  @When("the admin attempts to delete the guide account linked to the {string} \\(p12)")
  public void the_admin_attempts_to_delete_the_guide_account_linked_to_the_p12(String string) {
    GuideController.deleteGuide(string);
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the specified guide does
   * not exist in the system
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Email of the guide which should not exist in the divesafe system instance
   */
  @Then("the guide account linked to the {string} shall not exist in the system \\(p12)")
  public void the_guide_account_linked_to_the_shall_not_exist_in_the_system_p12(String string) {
    Assert.assertNull(findGuideFromEmail(string));
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the amount of guides in
   * the system is the same as the number specified
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Expected number of guides in the divesafe system instance in the form of a
   *        string
   */
  @Then("the number of guides in the system is {string} \\(p12)")
  public void the_number_of_guides_in_the_system_is_p12(String string) {
    Assert.assertEquals(Integer.parseInt(string), diveSafe.numberOfGuides());
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the specified member
   * exists in the system
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Email of the member which should exist in the system
   */
  @Then("the member account linked to the {string} shall exist in the system \\(p12)")
  public void the_member_account_linked_to_the_shall_exist_in_the_system_p12(String string) {
    Assert.assertNotNull(findMemberFromEmail(string));
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the number of guides in
   * the system is equal to the Integer specified
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param int1 Expected number of guides in the divesafe system instance in the form of an
   *        Integer
   */
  @Then("the number of guides in the system is {int} \\(p12)")
  public void the_number_of_guides_in_the_system_is_p12(Integer int1) {
    Assert.assertEquals(int1, Integer.valueOf(diveSafe.numberOfGuides()));
  }

  /**
   * Helper method used to find a guide of the divesafe system from its email address
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param email Email of the guide to find in the divesafe system instance
   * @return
   */
  private Guide findGuideFromEmail(String email) {
    List<Guide> guideList = DiveSafeApplication.getDiveSafe().getGuides();
    for (Guide g : guideList) {
      if (g.getEmail().equals(email)) {
        return g;
      }
    }
    return null;
  }

  /**
   * Helper method used to find a member of the divesafe system from its email address
   *
   * @author C�dric Barr�, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param email Email of the member to find in the divesafe system instance
   * @return
   */
  private Member findMemberFromEmail(String email) {
    List<Member> memberList = DiveSafeApplication.getDiveSafe().getMembers();
    for (Member m : memberList) {
      if (m.getEmail().equals(email)) {
        return m;
      }
    }
    return null;
  }

}
