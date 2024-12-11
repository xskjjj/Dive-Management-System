package ca.mcgill.ecse.divesafe.controller;

import java.util.List;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Guide;
import ca.mcgill.ecse.divesafe.model.Item;
import ca.mcgill.ecse.divesafe.model.ItemBooking;
import ca.mcgill.ecse.divesafe.model.Member;

import ca.mcgill.ecse.divesafe.model.User;
import ca.mcgill.ecse.divesafe.persistence.DiveSafePersistence;

public class MemberController {

  private static DiveSafe diveSafe = DiveSafeApplication.getDiveSafe();

  private MemberController() {}

  /**
   * Registers members.
   *
   * @author Joey Koay
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrDays
   * @param guideRequired
   * @param hotelRequired
   * @param itemNames
   * @param itemQuantities
   * @throws InvalidInputException
   */
  public static String registerMember(String email, String password, String name,
      String emergencyContact, int nrDays, boolean guideRequired, boolean hotelRequired,
      List<String> itemNames, List<Integer> itemQuantities) {

    var error = checkCommonConditions(password, name, emergencyContact, nrDays, itemNames);

    if (email.contains(" ")) {
      error = "The email must not contain any spaces";
    }

    if (!validEmail(email)) {
      error = "Invalid email";
    }

    if (Member.hasWithEmail(email)) {
      error = "A member with this email already exists";
    }

    if (email.equals("admin@ad.atl")) {
      error = "The email entered is not allowed for members";
    }

    if (Guide.hasWithEmail(email)) {
      error = "A guide with this email already exists";
    }

    if (!error.isBlank()) {
      return error.trim();
    }
 
   	Member member = diveSafe.addMember(email, password, name, emergencyContact, nrDays,
    	        guideRequired, hotelRequired);
    
    for (int i = 0; i < itemNames.size(); i++) {
      try {
    		diveSafe.addItemBooking(itemQuantities.get(i), member, Item.getWithName(itemNames.get(i)));
        	DiveSafePersistence.save();
        } catch (RuntimeException e) {
        	return e.getMessage();
        }
    }

    return "";
  }

  /**
   * update members
   *
   * @author Joey Koay
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @param newNrDays
   * @param newGuideRequired
   * @param newHotelRequired
   * @param newItemNames
   * @param newItemQuantities
   * @throws InvalidInputException
   */
  public static String updateMember(String email, String newPassword, String newName,
      String newEmergencyContact, int newNrDays, boolean newGuideRequired, boolean newHotelRequired,
      List<String> newItemNames, List<Integer> newItemQuantities) {

    var error =
        checkCommonConditions(newPassword, newName, newEmergencyContact, newNrDays, newItemNames);

    if (!Member.hasWithEmail(email)) {
      error = "Member not found";
    }

    if (!error.isBlank()) {
      return error.trim();
    }

    Member member = Member.getWithEmail(email);
    member.setPassword(newPassword);
    member.setName(newName);
    member.setEmergencyContact(newEmergencyContact);
    member.setNumDays(newNrDays);
    member.setGuideRequired(newGuideRequired);
    member.setHotelRequired(newHotelRequired);

    List<ItemBooking> bookedItems = member.getItemBookings();
    while (bookedItems.size() != 0) {
      member.getItemBookings().get(0).delete();
    }
    for (int i = 0; i < newItemNames.size(); i++) {
      try {
    		diveSafe.addItemBooking(newItemQuantities.get(i), member,
    		          Item.getWithName(newItemNames.get(i)));
        	DiveSafePersistence.save();
        } catch (RuntimeException e) {
        	return e.getMessage();
        }
    }

    return "";
  }

  public static String deleteMember(String email) {
    Member member = Member.getWithEmail(email);
    if (member != null) {
      try {
    		member.delete();
        DiveSafePersistence.save();
        } catch (RuntimeException e) {
        	return e.getMessage();
        }
    }
    return "";
  }

  /**
   * checking if the email is valid or not
   *
   * @author Joey Koay
   * @param email
   * @return whether the email is valid
   */
  private static boolean validEmail(String email) {
    boolean validEmail = true;
    if (email == null || email.length() == 0) {
      validEmail = false;
    }
    if (!(email.indexOf("@") > 0)) {
      validEmail = false;
    }
    if (!(email.indexOf("@") == email.lastIndexOf("@"))) {
      validEmail = false;
    }
    if (!(email.indexOf("@") < email.lastIndexOf(".") - 1)) {
      validEmail = false;
    }
    if (!(email.lastIndexOf(".") < email.length() - 1)) {
      validEmail = false;
    }
    return validEmail;
  }

  /**
   * checking if the password is valid or not
   *
   * @author Joey Koay
   * @param string
   * @return whether the is null or empty
   */
  private static boolean validateNotEmpty(String string) {
    return string != null && !string.isBlank();
  }

  /**
   * checking if the number of days is valid or not
   *
   * @author Joey Koay
   * @param nrDays - number of days
   * @param diveSafe - the application
   * @return whether the number of days is valid
   */
  private static boolean validNrDays(int nrDays, DiveSafe diveSafe) {
    return nrDays > 0 && nrDays <= diveSafe.getNumDays();
  }

  /**
   * checking if the item exists
   *
   * @author Joey Koay
   * @param newItemsNames - a list of all of the itemNames
   * @param diveSafe - the application
   * @return whether the item exists or not
   */
  private static boolean validItems(List<String> itemNames, DiveSafe diveSafe) {
    for (String name : itemNames) {
      if (!Item.hasWithName(name)) {
        return false;
      }
    }
    return true;
  }

  private static String checkCommonConditions(String password, String name, String emergencyContact,
      int nrDays, List<String> itemNames) {
    String error = "";
    if (!validateNotEmpty(password)) {
      error = "The password cannot be empty";
    }

    if (!validateNotEmpty(name)) {
      error = "The name cannot be empty";
    }

    if (!validateNotEmpty(emergencyContact)) {
      error = "The emergency contact cannot be empty";
    }

    if (!validNrDays(nrDays, diveSafe)) {
      error =
          "The number of days must be greater than zero and less than or equal to the number of diving days in the diving season";
    }

    if (!validItems(itemNames, diveSafe)) {
      error = "Requested item not found";
    }

    return error;
  }

}
