package ca.mcgill.ecse.divesafe.features;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.controller.AssignmentController;
import ca.mcgill.ecse.divesafe.model.Assignment;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.Member;
import ca.mcgill.ecse.divesafe.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssignmentFeatureStepDefinitions {
	private DiveSafe diveSafe;
	private String error;
	//@Yiqiao Wang
  @Given("the following DiveSafe system exists:")
  public void the_following_dive_safe_system_exists(io.cucumber.datatable.DataTable dataTable) {

	    List<Map<String, String>> diveSafe1 = dataTable.asMaps(String.class, String.class);
	    var date = diveSafe1.get(0).get("startDate");
	    var days = diveSafe1.get(0).get("numDays");
	    var price = diveSafe1.get(0).get("priceOfGuidePerDay");
	    error="";
	    diveSafe = DiveSafeApplication.getDiveSafe();
	    diveSafe.setStartDate(java.sql.Date.valueOf(date));
	    diveSafe.setNumDays(Integer.parseInt(days));
	    diveSafe.setPriceOfGuidePerDay(Integer.parseInt(price));
	  
  }
  //@Yiqiao Wang
  @Given("the following pieces of equipment exist in the system:")
  public void the_following_pieces_of_equipment_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
	    List<Map<String, String>> equipmentInfo = dataTable.asMaps(String.class, String.class);

	    for (Map<String, String> equipment : equipmentInfo) {
	      var name = equipment.get("name");
	      var weight = equipment.get("weight");
	      var pricePerDay = equipment.get("pricePerDay");
	      new Equipment(name, Integer.parseInt(weight), Integer.parseInt(pricePerDay), diveSafe);
  
	    }
  }
  //@Yiqiao Wang
  @Given("the following equipment bundles exist in the system:")
  public void the_following_equipment_bundles_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> equipmentBundleInfo = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> equipmentBundle : equipmentBundleInfo) {
      var name = equipmentBundle.get("name");
      var discount = equipmentBundle.get("discount");
      List<String> equipmentsInBundle = Arrays.asList(equipmentBundle.get("items").split(","));
      List<String> equipmentQuantity = Arrays.asList(equipmentBundle.get("quantity").split(","));
      EquipmentBundle equipmentBundle1 =
          new EquipmentBundle(name, Integer.parseInt(discount), diveSafe);

      for (int i = 0; i < equipmentsInBundle.size(); i++) {
        new BundleItem(Integer.parseInt(equipmentQuantity.get(i)), diveSafe, equipmentBundle1,
            (Equipment) Item.getWithName(equipmentsInBundle.get(i)));
      }
    }
  }
  //@Yiqiao Wang
  @Given("the following guides exist in the system:")
  public void the_following_guides_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String,String>> guideInfo = dataTable.asMaps(String.class, String.class);
    
    for (Map<String, String> guide : guideInfo) {
    	var email = guide.get("email");
    	var password = guide.get("password");
    	var name = guide.get("name");
    	var emergencyContact = guide.get("emergencyContact");
    	new Guide(email, password, name, emergencyContact, diveSafe);
    }
  }
  //@Yiqiao Wang
  @Given("the following members exist in the system:")
  public void the_following_members_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    List<Map<String,String>> memberInfo = dataTable.asMaps(String.class, String.class);
	    
	    for (Map<String, String> member : memberInfo) {
	    	var email = member.get("email");
	    	var password = member.get("password");
	    	var name = member.get("name");
	    	var emergencyContact = member.get("emergencyContact");
	    	var numDays = member.get("numDays");
	    	var guideRequired = member.get("guideRequired");
	    	var hotelRequired = member.get("hotelRequired");
	    	List<String> itemBookings = Arrays.asList(member.get("itemBookings").split(","));
	    	List<String> itemBookingQuantities = Arrays.asList(member.get("itemBookingQuantities").split(","));
	    	Member newMember = new Member(email,password,name,emergencyContact,Integer.parseInt(numDays),Boolean.parseBoolean(guideRequired),Boolean.parseBoolean(hotelRequired),diveSafe);
	    	
	    	for (int i = 0; i < itemBookings.size(); i++) {
	    		newMember.addItemBooking(Integer.parseInt(itemBookingQuantities.get(i)), diveSafe, Item.getWithName(itemBookings.get(i)));
	    	}	
	    }
  }
  //@Steven Tian
  @When("the administrator attempts to initiate the assignment process")
  public void the_administrator_attempts_to_initiate_the_assignment_process() {
	  error+=AssignmentController.initiateAssignment();
  }
  //@Steven Tian
  @Then("the following assignments shall exist in the system:")
  public void the_following_assignments_shall_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String,String>> assignmentInfo = dataTable.asMaps(String.class, String.class);
	  int counter=0;
	  for (Map<String, String> assignment: assignmentInfo) {
		  String memberEmail = assignment.get("memberEmail");
		  Member member = (Member) User.getWithEmail(memberEmail);
		  int startday = Integer.parseInt(assignment.get("startDay"));
		  int endday = Integer.parseInt(assignment.get("endDay"));
		  String guideemail = assignment.get("guideEmail");
		  if(guideemail==null) {
			  guideemail = "";
		  }
		  Assignment assign = (member).getAssignment();
		  String g = "";
		  if (assign.hasGuide()){
			  g = assign.getGuide().getEmail();
		  }
		  counter+=1;
		  if (!((g.equals(guideemail))&&(startday == assign.getStartDay())&&(endday ==assign.getEndDay()))) {
			  
			  fail(g.concat((String.valueOf(counter))).concat(guideemail).concat(String.valueOf(startday)).concat(String.valueOf(assign.getStartDay())).concat(String.valueOf(endday)).concat(String.valueOf(assign.getEndDay())));
		  }
	  }
  }
  
  //@Steven Tian
  @Then("the assignment for {string} shall be marked as {string}")
  public void the_assignment_for_shall_be_marked_as(String string, String string2) {
	  Member member = Member.getWithEmail(string);
	  Assignment assignment = member.getAssignment();
	  System.out.println(assignment.getAssignmentStatusFullName());
	  System.out.println(assignment.getPaymentStatusFullName());
	  if(!(string2.equals(assignment.getPaymentStatusFullName()) || string2.equals(assignment.getAssignmentStatusFullName()))){
		  fail("The assignment is not marked as "+string2);
	  }
	  
  }
  //@Yiqiao Wang
  @Then("the number of assignments in the system shall be {string}")
  public void the_number_of_assignments_in_the_system_shall_be(String string) {
	  List<Assignment> assignmentList = diveSafe.getAssignments();
	  assertEquals(Integer.parseInt(string),assignmentList.size());
  }
  //@Yiqiao Wang
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
	assertTrue(error.contains(string));
  }
  //@Yiqiao Wang
  @Given("the following assignments exist in the system:")
  public void the_following_assignments_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String,String>> assignmentInfo = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> assignment : assignmentInfo) {
			var memberEmail = assignment.get("memberEmail");
			var guideEmail = assignment.get("guideEmail");
			var startDay = assignment.get("startDay");
			var endDay = assignment.get("endDay");
			Assignment assignment1 = new Assignment(Integer.parseInt(startDay), Integer.parseInt(endDay), (Member) User.getWithEmail(memberEmail), diveSafe);
			assignment1.setGuide((Guide) User.getWithEmail(guideEmail));
		}
  }
  //@Steven Tian
  @When("the administrator attempts to confirm payment for {string} using authorization code {string}")
  public void the_administrator_attempts_to_confirm_payment_for_using_authorization_code(String string, String string2) {
	  error+=AssignmentController.confirmPayment(string,string2);
  }
  
  

  @Then("the assignment for {string} shall record the authorization code {string}")
  public void the_assignment_for_shall_record_the_authorization_code(String string,
      String string2) {
	  Assignment assignment = Member.getWithEmail(string).getAssignment();
	  assertEquals(string2,assignment.getCode());
  }

  @Then("the member account with the email {string} does not exist")
  public void the_member_account_with_the_email_does_not_exist(String string) {
	var user = User.getWithEmail(string);
	assertTrue(user == null);   
  }
  //@Steven Tian
  @Then("there are {string} members in the system")
  public void there_are_members_in_the_system(String string) {
	  List<Member> members = diveSafe.getMembers();
	  int numberofmember = members.size();
	  assertEquals(Integer.parseInt(string),numberofmember);
  }
  //@Steven Tian
  @Then("the error {string} shall be raised")
  public void the_error_shall_be_raised(String string) {
	  System.out.println(error);
	  assertTrue(error.contains(string));
  }
  //Steven Tian
  @When("the administrator attempts to cancel the trip for {string}")
  public void the_administrator_attempts_to_cancel_the_trip_for(String string) {
	  error+=AssignmentController.cancelTrip(string);
  }
//@Yiqiao Wang
  @Given("the member with {string} has paid for their trip")
  public void the_member_with_has_paid_for_their_trip(String string) {
	(((Member)User.getWithEmail(string)).getAssignment()).togglePaymentStatus();
  }
  //@Steven Tian
  @Then("the member with email address {string} shall receive a refund of {string} percent")
  public void the_member_with_email_address_shall_receive_a_refund_of_percent(String string,String string2) {
	  Assignment assignment = ((Member)User.getWithEmail(string)).getAssignment();
	  String status = assignment.getAssignmentStatusFullName();
	  if(status == "Finished") {
		  assertEquals(string2,"0");
	  }
	  if(status == "Started") {
		  assertEquals(string2,"10");
	  }
	  if(status == "Paid") {
		  assertEquals(string2,"50");
	  }
  }
//@Yiqiao Wang
  @Given("the member with {string} has started their trip")
  public void the_member_with_has_started_their_trip(String string) {
    (((Member)User.getWithEmail(string)).getAssignment()).toggleToStar();
  }
  //@Steven Tian
  @When("the administrator attempts to finish the trip for the member with email {string}")
  public void the_administrator_attempts_to_finish_the_trip_for_the_member_with_email(String string) {
	error+=AssignmentController.finishTrip(string);
  }
//@Yiqiao Wang
  @Given("the member with {string} is banned")
  public void the_member_with_is_banned(String string) {
	(((Member)User.getWithEmail(string))).togglebanStatus();
  }

  @Then("the member with email {string} shall be {string}")
  public void the_member_with_email_shall_be(String string, String string2) {
    Member member = (Member) User.getWithEmail(string);
    String memberstatus = member.getBanStatusFullName();
    assertEquals(string2,memberstatus);
  }
  //@Steven Tian
  @When("the administrator attempts to start the trips for day  {string}")
  public void the_administrator_attempts_to_start_the_trips_for_day(String string) {
	  error+=AssignmentController.startTripsForDay(Integer.parseInt(string));
  }
//@Yiqiao Wang
  @Given("the member with {string} has cancelled their trip")
  public void the_member_with_has_cancelled_their_trip(String string) {
	(((Member)User.getWithEmail(string)).getAssignment()).toggleToCan();
  }
//@Yiqiao Wang
  @Given("the member with {string} has finished their trip")
  public void the_member_with_has_finished_their_trip(String string) {
  (((Member)User.getWithEmail(string)).getAssignment()).toggleToStar();
  (((Member)User.getWithEmail(string)).getAssignment()).toggleToFin();
  System.out.println(((Member)User.getWithEmail(string)).getAssignment().getAssignmentStatusFullName());
  }
  
  
}
