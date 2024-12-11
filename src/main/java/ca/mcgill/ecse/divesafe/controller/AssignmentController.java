package ca.mcgill.ecse.divesafe.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.model.Assignment;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.Member;
import ca.mcgill.ecse.divesafe.persistence.*;

public class AssignmentController {

  private static DiveSafe diveSafe = DiveSafeApplication.getDiveSafe();

  private AssignmentController() {}

  public static List<TOAssignment> getAssignments() {
    List<TOAssignment> assignments = new ArrayList<>();

    for (var assignment : diveSafe.getAssignments()) {
      var newTOAssignment = wrapAssignment(assignment);
    	  assignments.add(newTOAssignment);
    }
    return assignments;
  }


/**
 * This method initiate assignments for all members depending on their requirements
 * @author Yiqiao Wang
 * @return error messages
 */
  public static String initiateAssignment() {
	String error = "";
	List<Member> members = diveSafe.getMembers();
	List<Guide> guides = diveSafe.getGuides();
	for (Member member : members) {
		int numDay = member.getNumDays();
		if (member.getGuideRequired()) {
			for (Guide guide : guides) {
				if (guide.remainingDays()>=numDay) {
					int startDate = diveSafe.getNumDays()-guide.remainingDays()+1;
					int endDate = startDate+numDay-1;
					Assignment a = new Assignment(startDate,endDate,member,diveSafe);
					a.setGuide(guide);
					break;
				}
			}
		}
		else {
			new Assignment(1,numDay,member,diveSafe);
		}
	}
	
	if (diveSafe.getAssignments().size()!=diveSafe.getMembers().size()) {
		error = "Assignments could not be completed for all members";
	}
    return error;
  }

  /**
   * This method transmit to state "TripCancelled". This removes all assignment of the member and handles the refund.
   * @author Yixuan Qin
   * @param userEmail - email of the user canceling trip
   * @return error messages
   */
  public static String cancelTrip(String userEmail) {
	  String error = "";
	  Member member = Member.getWithEmail(userEmail); 
	  if (member==null) {
		  error = "Member with email address "+userEmail+" does not exist";
	  }else {
		  Assignment assignment = member.getAssignment();
		  if (assignment == null) {
			  return "";
		  }
		  String assignmentStatus = assignment.getAssignmentStatusFullName();
		  String banStatus = member.getBanStatusFullName();
		  
		  if (assignmentStatus == "Finished") {
			  error = "Cannot cancel a trip which has finished";
		  }
		  else if (banStatus == "Banned") {
			  error = "Cannot cancel the trip due to a ban";
		  }
		  else {
			  assignment.toggleToCan();
		  }
	  }
    return error;
  }
  /**
   * This method put the member into "Finished" state. 
   * @author Yongru Pan
   * @param userEmail - the user name a member uses to perform action in diveSafe
   * @return msg - state change messages
   * @return error - error message when attempts fail
   */

  public static String finishTrip(String userEmail) {
	  String error = "";
	  Member member = Member.getWithEmail(userEmail); 
	  if (member == null) {
		  error += "Member with email address " + userEmail +" does not exist";
		  return error;
	  }
	  Assignment assignment = member.getAssignment();
	  String assignmentStatus = assignment.getAssignmentStatusFullName();
	  String banStatus = member.getBanStatusFullName();
	  if (assignmentStatus == "Cancelled") {
		  error = "Cannot finish a trip which has been cancelled";
	  }else if (banStatus == "Banned") {
		  error = "Cannot finish the trip due to a ban";
	  }
	  else if(assignmentStatus == "Assigned"){
		  error = "Cannot finish a trip which has not started";
	  } 
	  else {
		  assignment.toggleToFin();
	  }
	  
    return error;
  }
  /**
   * This method put the assignments into "Started" state base on which day it should start. 
   * @author Steven Tian
   * @param day - the day the assignment should start
   * @return error - error message when attempts fail
   */
  public static String startTripsForDay(int day) {
	  String error = "";
	  List<Assignment> assignmentlist = diveSafe.getAssignments();
	  for(Assignment assignment:assignmentlist) {
		  Member member = assignment.getMember();
		  if(assignment.getStartDay() == day) {
			  if(member.getBanStatusFullName().equals("Banned")) {
				  error = "Cannot start the trip due to a ban";
			  }
			  else if(assignment.getAssignmentStatusFullName().equals("Cancelled")){
				  error = "Cannot start a trip which has been cancelled";
			  }
			  else if(assignment.getAssignmentStatusFullName().equals("Finished")) {
				  error = "Cannot start a trip which has finished";
			  }else {
				  if(assignment.getPaymentStatusFullName().equals("NotPaid")) {
					  member.togglebanStatus();
				  }
				  assignment.toggleToStar(); 
			  }			  
		  }
	  }
	  return error;
  }

 /**
  * This method checks out whether the payment is paid or not. 
  * @param Yuxin Wang
  * @param userEmail - the username of a member who is registered in DiveSafe
  * @param authorizationCode - the code to verify the payment is actually accomplished
  * @return error - error message when attempts fail
  */
  
  public static String confirmPayment(String userEmail, String authorizationCode) {
    String error = "";
    String code = authorizationCode;
    Member member = Member.getWithEmail(userEmail);
    if (member == null) {
    	error = "Member with email address "+ userEmail +" does not exist";
    }else {
    	String memberStatus = member.getBanStatusFullName();
        Assignment assignment = member.getAssignment();
        String paymentStatus = assignment.getPaymentStatusFullName();
        String assignmentStatus = assignment.getAssignmentStatusFullName();
        if (code == null || code.equals("")) {
          error ="Invalid authorization code";
        }
        else {
          if (paymentStatus == "Paid") {
            error += "Trip has already been paid for";
            }
          else if (assignmentStatus == "Finished") {
              error +="Cannot pay for a trip which has finished";
          }
          else if(assignmentStatus == "Started") {
        	error += "Trip has already been paid for";
          }
          else if(memberStatus == "Banned") {
        	error += "Cannot pay for the trip due to a ban";
          }
          else if (assignmentStatus == "Cancelled") {
            error += "Cannot pay for a trip which has been cancelled";
          }
          else {
        	  assignment.setCode(code);
        	  assignment.togglePaymentStatus();
          	}
          }
    }
    return error;
  }
    
        
  /**
   * This method put the member into "Banned" state. This stop member from doing all actions in diveSafe.
   * @author Yongru Pan
   * @param userEmail - the user name a member uses to perform action in diveSafe
   * @return msg - state change messages
   */

  public static String toggleBan(String userEmail) {
	  String msg = "Banned";
	  Member member = Member.getWithEmail(userEmail);
	  member.togglebanStatus();
	  return msg;
  }

  /**
   * Helper method used to wrap the information in an <code>Assignment</code> instance in an
   * instance of <code>TOAssignment</code>.
   *
   * @author Harrison Wang Oct 19, 2021
   * @param assignment - The <code>Assignment</code> instance to transfer the information from.
   * @return A <code>TOAssignment</code> instance containing the information in the
   *         <code>Assignment</code> parameter.
   */
  private static TOAssignment wrapAssignment(Assignment assignment) {
    var member = assignment.getMember();

    // Initialize values for all necessary parameters.
    String memberEmail = member.getEmail();
    String memberName = member.getName();
    String guideEmail = assignment.hasGuide() ? assignment.getGuide().getEmail() : "";
    String guideName = assignment.hasGuide() ? assignment.getGuide().getName() : "";
    String hotelName = assignment.hasHotel() ? assignment.getHotel().getName() : "";

    int numDays = member.getNumDays();
    int startDay = assignment.getStartDay();
    int endDay = assignment.getEndDay();
    int totalCostForGuide = assignment.hasGuide() ? numDays * diveSafe.getPriceOfGuidePerDay() : 0;
    /*
     * Calculate the totalCostForEquipment.
     *
     * Sum the costs of all booked items depending on if they are an Equipment or EquipmentBundle
     * instance to get the equipmentCostPerDay for this assignment.
     *
     * Multiply equipmentCostPerDay by nrOfDays to get totalCostForEquipment.
     */
    int equipmentCostPerDay = 0;
    for (var bookedItem : member.getItemBookings()) {
      Item item = bookedItem.getItem();
      if (item instanceof Equipment equipment) {
        equipmentCostPerDay += equipment.getPricePerDay() * bookedItem.getQuantity();
      } else if (item instanceof EquipmentBundle bundle) {
        int bundleCost = 0;
        for (var bundledItem : bundle.getBundleItems()) {
          bundleCost += bundledItem.getEquipment().getPricePerDay() * bundledItem.getQuantity();
        }
        // Discount only applicable if assignment includes guide, so check for that before applying discount
        if (assignment.hasGuide()) {
          bundleCost = (int) (bundleCost * ((100.0 - bundle.getDiscount()) / 100.0));
        }
        equipmentCostPerDay += bundleCost * bookedItem.getQuantity();
      }
    }
    int totalCostForEquipment = equipmentCostPerDay * numDays;

    return new TOAssignment(memberEmail, memberName, guideEmail, guideName, hotelName, startDay,
        endDay, totalCostForGuide, totalCostForEquipment);
  }

}
