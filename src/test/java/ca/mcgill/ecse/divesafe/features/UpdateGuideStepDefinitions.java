package ca.mcgill.ecse.divesafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.GuideController;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Guide;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * The step definitions were mostly implemented as a team, all the members of the team contributed
 * and worked on the implementation of all the methods, not only to the ones they were assigned to.
 */

public class UpdateGuideStepDefinitions {
  private DiveSafe diveSafe;
  private String error;
  private int errorCntr;

  /**
   * This function creates the DiveSafe system and sets its parameters, and initialize the error
   * message and error count.
   *
   * @author Can Akin, Maxime Drouin
   * @param dataTable
   */

  @Given("the following DiveSafe system exists: \\(p11)")
  public void the_following_dive_safe_system_exists_p11(
      io.cucumber.datatable.DataTable dataTable) {
    diveSafe = DiveSafeApplication.getDiveSafe();

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) { // set the attributes of the diveSafe system
      diveSafe.setStartDate(Date.valueOf(row.get("startDate")));
      diveSafe.setNumDays(Integer.parseInt(row.get("numDays")));
      diveSafe.setPriceOfGuidePerDay(Integer.parseInt(row.get("priceOfGuidePerDay")));
    }

    error = ""; // initialize the error string to be empty
    errorCntr = 0; // initialize the error count to 0

  }

  /**
   * This function creates the guides with the given information form the Table into the diveSafe
   * system.
   *
   * @author Anaëlle Drai Laguéns
   * @param dataTable
   */

  @Given("the following guides exist in the system: \\(p11)")
  public void the_following_guides_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(); // retrieve the information from the dataTable

    for (Map<String, String> row : rows) { // Create guides with the informations provided in the dataTable
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      diveSafe.addGuide(email, password, name, emergencyContact);
    }

  }

  /**
   * This method calls the controller method and updates the guide information using it.
   *
   * @author Oliver Cafferty
   * @param email, newPassword, newName, newEmergencyContact
   *
   */

  @When("the guide with {string} attempts to update their account information to {string}, {string}, and {string} \\(p11)")
  public void the_guide_with_attempts_to_update_their_account_information_to_and_p11(String email,
      String newPassword, String newName, String newEmergencyContact) {
    // Try executing the update guide method from the controller with the given
    // strings, and if an error occurs, increment error count and store the message
    callController(GuideController.updateGuide(email, newPassword, newName, newEmergencyContact));
  }

  /**
   * This method asserts the error count is 0 and message is empty, finds the guide in the system
   * and checks whether the updated informations match the provided strings
   *
   * @author Samuel Snodgrass
   * @param email, newPassword, newName, newEmergencyContact
   */

  @Then("their guide account information will be updated and is now {string}, {string}, {string}, and {string} \\(p11)")
  public void their_guide_account_information_will_be_updated_and_is_now_and_p11(String email,
      String newPassword, String newName, String newEmergencyContact) {
    // check there was no error by checking the error string is empty and the
    // errorCntr is equal to 0
    assertEquals("", error);
    assertEquals(0, errorCntr);

    Guide guide = Guide.getWithEmail(email); // find the guide that was updated using its
                                                     // unique email

    // check if its email, password, name and emergency contacts match the updated
    // information
    assertEquals(email, guide.getEmail());
    assertEquals(newPassword, guide.getPassword());
    assertEquals(newName, guide.getName());
    assertEquals(newEmergencyContact, guide.getEmergencyContact());
  }

  /**
   * This method asserts whether the number of guides in the system corresponds to the provided
   * integer.
   *
   * @author Lee Brickman
   * @param numOfGuides
   */

  @Then("the number of guides in the system is {int} \\(p11)")
  public void the_number_of_guides_in_the_system_is_p11(Integer numOfGuides) {

    assertEquals(numOfGuides, diveSafe.getGuides().size()); // check the number of guides in the
                                                             // system corresponds
                                                             // to the integer
  }

  /**
   * This method asserts whether the error string (which means the error that was caught), matches
   * the required error string
   *
   * @author Lee Brickman
   * @param errorMessage
   */

  @Then("the following {string} shall be raised \\(p11)")
  public void the_following_shall_be_raised_p11(String errorMessage) {

    assertTrue(error.contains(errorMessage)); // check the error string corresponds to the given
                                              // string
  }

  /**
   * This method asserts the guide information was not changed and corresponds to the previous
   * information if an error occurs.
   *
   * @author Lee Brickman, Samuel Snodgrass, Oliver Cafferty, Can Akin, Maxime Drouin, Anaëlle Drai
   *         Laguéns
   * @param email, password, name, emergencyContact
   */

  @Then("the guide account information will not be updated and will keep {string}, {string}, {string}, and {string} \\(p11)")
  public void the_guide_account_information_will_not_be_updated_and_will_keep_and_p11(String email,
      String password, String name, String emergencyContact) {

    var guide = Guide.getWithEmail(email); // find the guide that was updated using its unique email

    // check its information corresponds to the strings which correspond to his
    // previous informations
    assertEquals(email, guide.getEmail());
    assertEquals(password, guide.getPassword());
    assertEquals(name, guide.getName());
    assertEquals(emergencyContact, guide.getEmergencyContact());

  }

  /**
   * @author Oliver Cafferty
   */
  private void callController(String controllerResult) {
    if (!controllerResult.isEmpty()) {
      error += controllerResult;
      errorCntr += 1;
    }
  }

}
