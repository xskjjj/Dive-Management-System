package ca.mcgill.ecse.divesafe.controller;

import java.util.Arrays;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Hotel;
import ca.mcgill.ecse.divesafe.model.Hotel.HotelType;
import ca.mcgill.ecse.divesafe.persistence.DiveSafePersistence;

public class HotelController {

  private static DiveSafe diveSafe = DiveSafeApplication.getDiveSafe();

  private HotelController() {}

  // note: the hotel rating is its number of stars
  public static String addHotel(String name, String address, int nrStars, String type) {
    String error = checkCommonConditions(name, address, nrStars, type);

    if (Hotel.hasWithName(name)) {
      error = "Hotel already exists in the system";
    }

    if (!error.isBlank()) {
      return error.trim();
    }

    HotelType hotelType = HotelType.valueOf(type);
    try {
    	 diveSafe.addHotel(name, address, nrStars, hotelType);
    	 DiveSafePersistence.save();
    } catch (RuntimeException e) {
    	return e.getMessage();
    }
    
    return "";
  }

  public static String updateHotel(String oldName, String newName, String newAddress,
      int newNrStars, String type) {
    String error = checkCommonConditions(newName, newAddress, newNrStars, type);

    if (!Hotel.hasWithName(oldName)) {
      error = "Hotel does not exist in the system";
    }
    if (!oldName.equals(newName) && Hotel.hasWithName(newName)) {
      error = "New name already linked to another hotel";
    }

    if (!error.isBlank()) {
      return error;
    }

    HotelType hotelType = HotelType.valueOf(type);
    Hotel aHotel = Hotel.getWithName(oldName);
    aHotel.setName(newName);
    aHotel.setAddress(newAddress);
    aHotel.setRating(newNrStars);
    aHotel.setType(hotelType);

    return "";
  }

  public static String deleteHotel(String name) {
    Hotel hotel = Hotel.getWithName(name);
    if (hotel != null) {
      try {
    		hotel.delete();
       	 	DiveSafePersistence.save();
       } catch (RuntimeException e) {
       	return e.getMessage();
       }
    }
    return "";
  }

  private static boolean isHotelType(String type) {
    // iterate over HotelType values and see if any have a name that matches the input type
    return Arrays.stream(HotelType.values()).anyMatch(e -> e.name().equals(type));
  }

  private static String checkCommonConditions(String name, String address, int nrStars,
      String type) {
    String error = "";
    if (name == "") {
      error = "Name cannot be empty";
    } else if (address == "") {
      error = "Address cannot be empty";
    } else if (nrStars < 1 || nrStars > 5) {
      error = "Number of stars must be between 1 and 5";
    }

    if (!isHotelType(type)) {
      error = String.format("Hotel type %s does not exist", type);
    }

    return error;
  }

}
