package ca.mcgill.ecse.divesafe.controller;

import java.sql.Date;
import ca.mcgill.ecse.divesafe.application.DiveSafeApplication;

import ca.mcgill.ecse.divesafe.model.DiveSafe;

public class SetupController {

  private SetupController() {}

  public static String setup(Date startDate, int numDays, int priceOfGuidePerDay) {
    if (numDays < 0) {
      return "The number of diving days must be greater than or equal to zero";
    } else if (priceOfGuidePerDay < 0) {
      return "The price of guide per day must be greater than or equal to zero";
    } else {
      var diveSafe = DiveSafeApplication.getDiveSafe();
      diveSafe.setStartDate(startDate);
      diveSafe.setPriceOfGuidePerDay(priceOfGuidePerDay);
      diveSafe.setNumDays(numDays);
    }
    return "";
  }

}
