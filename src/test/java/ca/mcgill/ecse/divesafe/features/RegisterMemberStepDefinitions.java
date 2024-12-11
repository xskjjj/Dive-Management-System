package ca.mcgill.ecse.divesafe.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.MemberController;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.ItemBooking;
import ca.mcgill.ecse.divesafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterMemberStepDefinitions {
  private DiveSafe diveSafe;
  private String error;


  /**
   * @param dataTable
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Given("the following DiveSafe system exists: \\(p9)")
  public void the_following_dive_safe_system_exists_p9(io.cucumber.datatable.DataTable dataTable) {
    error = "";
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    diveSafe = DiveSafeApplication.getDiveSafe();
    for (Map<String, String> row : rows) {
      Date startDate = java.sql.Date.valueOf(row.get("startDate"));
      diveSafe.setStartDate(startDate);

      int numDays = Integer.parseInt(row.get("numDays"));
      diveSafe.setNumDays(numDays);

      int priceOfGuidePerDay = Integer.parseInt(row.get("priceOfGuidePerDay"));
      diveSafe.setPriceOfGuidePerDay(priceOfGuidePerDay);
    }
  }


  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & JoeyKoay & VictorMicha
   */
  @Given("the following equipment exists in the system: \\(p9)")
  public void the_following_equipment_exists_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> r : rows) {
      String name = r.get("name");
      int weight = Integer.parseInt(r.get("weight"));
      int pricePerDay = Integer.parseInt(r.get("pricePerDay"));
      new Equipment(name, weight, pricePerDay, this.diveSafe);
    }
  }

  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & EunjunChang & JoeyKoay & VictorMicha & SejongYoon
   */
  @Given("the following equipment bundles exist in the system: \\(p9)")
  public void the_following_equipment_bundles_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> r : rows) {
      String name = r.get("name");
      int discount = Integer.parseInt(r.get("discount"));
      EquipmentBundle bundle = new EquipmentBundle(name, discount, diveSafe);
      List<String> items = Arrays.asList(r.get("items").split(","));
      List<String> quantities = Arrays.asList(r.get("quantity").split(","));
      for (int i = 0; i < items.size(); i++) {
        new BundleItem(Integer.parseInt(quantities.get(i)), this.diveSafe, bundle,
            (Equipment) Equipment.getWithName(items.get(i)));
      }
    }
  }

  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & JoeyKoay & VictorMicha
   */
  @Given("the following members exist in the system: \\(p9)")
  public void the_following_members_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> r : rows) {
      String email = r.get("email");
      String password = r.get("password");
      String name = r.get("name");
      String emergencyContact = r.get("emergencyContact");
      int numDays = Integer.parseInt(r.get("numDays"));
      List<String> items = Arrays.asList(r.get("items").split(","));
      List<String> requestedQuantities = Arrays.asList(r.get("requestedQuantities").split(","));
      boolean guideRequired = Boolean.parseBoolean(r.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(r.get("hotelRequired"));
      Member m = new Member(email, password, name, emergencyContact, numDays, guideRequired,
          hotelRequired, this.diveSafe);

      for (int i = 0; i < items.size(); i++) {
        Item item = Item.getWithName(items.get(i));
        m.addItemBooking(Integer.parseInt(requestedQuantities.get(i)), this.diveSafe, item);
      }
    }
  }

  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & EunjunChang & JoeyKoay & VictorMicha & SejongYoon
   */
  @Given("the following guides exist in the system: \\(p9)")
  public void the_following_guides_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    List<Guide> guides = diveSafe.getGuides();
    List<String> emails = new ArrayList<String>();
    for (Guide g : guides)
      emails.add(g.getEmail());

    for (int i = 0; i < rows.size(); i++) {
      String email = rows.get(i).get("email");
      new Guide(email, rows.get(i).get("password"), rows.get(i).get("name"),
          rows.get(i).get("emergencyContact"), diveSafe);
    }
  }


  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param strNumDays
   * @param strItems
   * @param strItemQuantities
   * @param strGuideRequired
   * @param strHotelRequired
   * @author KaraBest & JoeyKoay & VictorMicha
   */

  @When("a new member attempts to register with {string} , {string} , {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p9)")
  public void a_new_member_attempts_to_register_with_and_p9(String email, String password,
      String name, String emergencyContact, String strNumDays, String strItems,
      String strItemQuantities, String strGuideRequired, String strHotelRequired) {
    List<String> items = Arrays.asList(strItems.split(","));
    List<Integer> itemQuantities = new ArrayList<Integer>();
    boolean guideRequired = Boolean.parseBoolean(strGuideRequired);
    boolean hotelRequired = Boolean.parseBoolean(strHotelRequired);
    int numDays = Integer.parseInt(strNumDays);
    for (String s : strItemQuantities.split(","))
      itemQuantities.add(Integer.parseInt(s));

    error = MemberController.registerMember(email, password, name, emergencyContact,
        numDays, guideRequired, hotelRequired, items, itemQuantities);
  }

  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param numDays
   * @param items
   * @param requestedQuantities
   * @param guideRequired
   * @param hotelRequired
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("a new member account shall exist with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p9)")
  public void a_new_member_account_shall_exist_with_and_p9(String email, String password,
      String name, String emergencyContact, String numDays, String items,
      String requestedQuantities, String guideRequired, String hotelRequired) {
    Member member = (Member) Member.getWithEmail(email);
    assertNotNull(member);
    assertEquals(password, member.getPassword());
    assertEquals(name, member.getName());
    assertEquals(emergencyContact, member.getEmergencyContact());
    assertEquals(Integer.parseInt(numDays), member.getNumDays());
    assertEquals(Boolean.parseBoolean(guideRequired), member.getGuideRequired());
    assertEquals(Boolean.parseBoolean(hotelRequired), member.getHotelRequired());
    List<String> itemNames = Arrays.asList(items.split(","));
    List<ItemBooking> itemsList = member.getItemBookings();
    List<String> quantities = Arrays.asList(requestedQuantities.split(","));
    assertEquals(itemsList.size(), itemNames.size());
    int index;
    for (ItemBooking bItem : itemsList) {
      String s = bItem.getItem().getName();
      assertTrue(itemNames.contains(s));
      index = itemNames.indexOf(s);
      assertEquals(Integer.parseInt(quantities.get(index)), bItem.getQuantity());
    }
  }

  /**
   * @param numMembers
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("there are {int} members in the system. \\(p9)")
  public void there_are_members_in_the_system_p9(Integer numMembers) {
    assertEquals(numMembers, (Integer) diveSafe.numberOfMembers());
  }

  /**
   * @param errorString
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("the following {string} shall be raised. \\(p9)")
  public void the_following_shall_be_raised_p9(String errorString) {
    assertTrue(error.contains(errorString));
  }

  /**
   * @param email
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("there is no member account for {string} \\(p9)")
  public void there_is_no_member_account_for_p9(String email) {
    List<Member> members = diveSafe.getMembers();
    for (int i = 0; i < diveSafe.numberOfMembers(); i++) {
      assertNotEquals(email, members.get(i).getEmail());
    }
  }

}
