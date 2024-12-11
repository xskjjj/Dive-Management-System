package ca.mcgill.ecse.divesafe.controller;

import java.util.List;

import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;
import ca.mcgill.ecse.divesafe.model.BundleItem;
import ca.mcgill.ecse.divesafe.model.DiveSafe;
import ca.mcgill.ecse.divesafe.model.Equipment;
import ca.mcgill.ecse.divesafe.model.EquipmentBundle;
import ca.mcgill.ecse.divesafe.persistence.DiveSafePersistence;

public class EquipmentController {

  private static DiveSafe diveSafe = DiveSafeApplication.getDiveSafe();

  private EquipmentController() {}

  public static String addEquipment(String name, int weight, int pricePerDay) {
    var error = checkCommonConditions(weight, name, pricePerDay);
    if (!error.isBlank()) {
      return error;
    }

    // Checks to see if equipment with same name already exists in the system
    if (Equipment.hasWithName(name)) {
      return "The piece of equipment already exists";
    }
    if (EquipmentBundle.hasWithName(name)) {
      return "The equipment bundle already exists";
    }

    try {
    diveSafe.addEquipment(name, weight, pricePerDay);
    DiveSafePersistence.save();
    } catch (RuntimeException e) {
    	return e.getMessage();
    }
    return "";
  }

  public static String updateEquipment(String oldName, String newName, int newWeight,
      int newPricePerDay) {
    var error = checkCommonConditions(newWeight, newName, newPricePerDay);

    if (!newName.equals(oldName)) {
      if (Equipment.hasWithName(newName)) {
        error = "The piece of equipment already exists";
      }
      if (EquipmentBundle.hasWithName(newName)) {
        error = "An equipment bundle with the same name already exists";
      }
    }

    var foundEquipment = Equipment.getWithName(oldName);
    if (foundEquipment == null) {
      error = "The piece of equipment does not exist";
    }

    if (!error.isBlank()) {
      return error.trim();
    }

    foundEquipment.setName(newName);
    foundEquipment.setWeight(newWeight);
    foundEquipment.setPricePerDay(newPricePerDay);

    return "";
  }

  public static String deleteEquipment(String name) {
    Equipment equipment = Equipment.getWithName(name);
    if (equipment == null) {
      return ""; // If no equipment with this name, nothing to do.
    }

    // Before delete any equipment, make sure its not a part of a bundle
    List<EquipmentBundle> bundles = diveSafe.getBundles();
    for (EquipmentBundle bundle : bundles) {
      for (BundleItem bundleItem : bundle.getBundleItems()) {
        if (bundleItem.getEquipment().getName().equals(equipment.getName())) {
          return "The piece of equipment is in a bundle and cannot be deleted";
        }
      }
    }
    
    //??
  try {
    equipment.delete();
    DiveSafePersistence.save();
    } catch (RuntimeException e) {
    	return e.getMessage();
    }
    return "";
  }

  private static String checkCommonConditions(int weight, String name, int pricePerDay) {
    // Checking for Invalid Input in for name
    if (name.trim().equals("")) {
      return "The name must not be empty";
    }
    // Checks for invalid weight inputs
    if (weight <= 0) {
      return "The weight must be greater than 0";
    }
    // Checks for invalid price per day inputs
    if (pricePerDay < 0) {
      return "The price per day must be greater than or equal to 0";
    }
    return "";
  }

}
