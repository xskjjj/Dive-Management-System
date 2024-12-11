/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.divesafe.controller;

// line 3 "../../../../../DiveSafeTransferObjects.ump"
public class TOAssignment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAssignment Attributes
  private String memberEmail;
  private String memberName;
  private String guideEmail;
  private String guideName;
  private String hotelName;
  private int startDay;
  private int endDay;
  private int totalCostForGuide;
  private int totalCostForEquipment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAssignment(String aMemberEmail, String aMemberName, String aGuideEmail, String aGuideName, String aHotelName, int aStartDay, int aEndDay, int aTotalCostForGuide, int aTotalCostForEquipment)
  {
    memberEmail = aMemberEmail;
    memberName = aMemberName;
    guideEmail = aGuideEmail;
    guideName = aGuideName;
    hotelName = aHotelName;
    startDay = aStartDay;
    endDay = aEndDay;
    totalCostForGuide = aTotalCostForGuide;
    totalCostForEquipment = aTotalCostForEquipment;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMemberEmail(String aMemberEmail)
  {
    boolean wasSet = false;
    memberEmail = aMemberEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setMemberName(String aMemberName)
  {
    boolean wasSet = false;
    memberName = aMemberName;
    wasSet = true;
    return wasSet;
  }

  public boolean setGuideEmail(String aGuideEmail)
  {
    boolean wasSet = false;
    guideEmail = aGuideEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setGuideName(String aGuideName)
  {
    boolean wasSet = false;
    guideName = aGuideName;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotelName(String aHotelName)
  {
    boolean wasSet = false;
    hotelName = aHotelName;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDay(int aStartDay)
  {
    boolean wasSet = false;
    startDay = aStartDay;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDay(int aEndDay)
  {
    boolean wasSet = false;
    endDay = aEndDay;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalCostForGuide(int aTotalCostForGuide)
  {
    boolean wasSet = false;
    totalCostForGuide = aTotalCostForGuide;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalCostForEquipment(int aTotalCostForEquipment)
  {
    boolean wasSet = false;
    totalCostForEquipment = aTotalCostForEquipment;
    wasSet = true;
    return wasSet;
  }

  public String getMemberEmail()
  {
    return memberEmail;
  }

  public String getMemberName()
  {
    return memberName;
  }

  public String getGuideEmail()
  {
    return guideEmail;
  }

  public String getGuideName()
  {
    return guideName;
  }

  public String getHotelName()
  {
    return hotelName;
  }

  public int getStartDay()
  {
    return startDay;
  }

  public int getEndDay()
  {
    return endDay;
  }

  public int getTotalCostForGuide()
  {
    return totalCostForGuide;
  }

  public int getTotalCostForEquipment()
  {
    return totalCostForEquipment;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "memberEmail" + ":" + getMemberEmail()+ "," +
            "memberName" + ":" + getMemberName()+ "," +
            "guideEmail" + ":" + getGuideEmail()+ "," +
            "guideName" + ":" + getGuideName()+ "," +
            "hotelName" + ":" + getHotelName()+ "," +
            "startDay" + ":" + getStartDay()+ "," +
            "endDay" + ":" + getEndDay()+ "," +
            "totalCostForGuide" + ":" + getTotalCostForGuide()+ "," +
            "totalCostForEquipment" + ":" + getTotalCostForEquipment()+ "]";
  }
}