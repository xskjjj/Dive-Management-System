/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.divesafe.model;
import java.util.*;

// line 1 "../../../../../DiveSafeStateMachine.ump"
// line 62 "../../../../../DiveSafe.ump"
// line 201 "../../../../../DiveSafe.ump"
public class Member extends NamedUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Member Attributes
  private int numDays;
  private boolean guideRequired;
  private boolean hotelRequired;

  //Member State Machines
  public enum BanStatus { Available, Banned }
  private BanStatus banStatus;
  public enum GuideStatus { WithoutGuide, WithGuide }
  private GuideStatus guideStatus;
  public enum RequiredStatus { RequiredGuide, NotRequiredGuide }
  private RequiredStatus requiredStatus;

  //Member Associations
  private DiveSafe diveSafe;
  private Assignment assignment;
  private List<ItemBooking> itemBookings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Member(String aEmail, String aPassword, String aName, String aEmergencyContact, int aNumDays, boolean aGuideRequired, boolean aHotelRequired, DiveSafe aDiveSafe)
  {
    super(aEmail, aPassword, aName, aEmergencyContact);
    numDays = aNumDays;
    guideRequired = aGuideRequired;
    hotelRequired = aHotelRequired;
    boolean didAddDiveSafe = setDiveSafe(aDiveSafe);
    if (!didAddDiveSafe)
    {
      throw new RuntimeException("Unable to create member due to diveSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    itemBookings = new ArrayList<ItemBooking>();
    setBanStatus(BanStatus.Available);
    setGuideStatus(GuideStatus.WithoutGuide);
    setRequiredStatus(RequiredStatus.RequiredGuide);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumDays(int aNumDays)
  {
    boolean wasSet = false;
    numDays = aNumDays;
    wasSet = true;
    return wasSet;
  }

  public boolean setGuideRequired(boolean aGuideRequired)
  {
    boolean wasSet = false;
    guideRequired = aGuideRequired;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotelRequired(boolean aHotelRequired)
  {
    boolean wasSet = false;
    hotelRequired = aHotelRequired;
    wasSet = true;
    return wasSet;
  }

  public int getNumDays()
  {
    return numDays;
  }

  public boolean getGuideRequired()
  {
    return guideRequired;
  }

  public boolean getHotelRequired()
  {
    return hotelRequired;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isGuideRequired()
  {
    return guideRequired;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHotelRequired()
  {
    return hotelRequired;
  }

  public String getBanStatusFullName()
  {
    String answer = banStatus.toString();
    return answer;
  }

  public String getGuideStatusFullName()
  {
    String answer = guideStatus.toString();
    return answer;
  }

  public String getRequiredStatusFullName()
  {
    String answer = requiredStatus.toString();
    return answer;
  }

  public BanStatus getBanStatus()
  {
    return banStatus;
  }

  public GuideStatus getGuideStatus()
  {
    return guideStatus;
  }

  public RequiredStatus getRequiredStatus()
  {
    return requiredStatus;
  }

  public boolean togglebanStatus()
  {
    boolean wasEventProcessed = false;
    
    BanStatus aBanStatus = banStatus;
    switch (aBanStatus)
    {
      case Available:
        setBanStatus(BanStatus.Banned);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean toggleguideStatus()
  {
    boolean wasEventProcessed = false;
    
    GuideStatus aGuideStatus = guideStatus;
    switch (aGuideStatus)
    {
      case WithoutGuide:
        setGuideStatus(GuideStatus.WithGuide);
        wasEventProcessed = true;
        break;
      case WithGuide:
        setGuideStatus(GuideStatus.WithoutGuide);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean togglerequiredStatus()
  {
    boolean wasEventProcessed = false;
    
    RequiredStatus aRequiredStatus = requiredStatus;
    switch (aRequiredStatus)
    {
      case RequiredGuide:
        setRequiredStatus(RequiredStatus.NotRequiredGuide);
        wasEventProcessed = true;
        break;
      case NotRequiredGuide:
        setRequiredStatus(RequiredStatus.RequiredGuide);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setBanStatus(BanStatus aBanStatus)
  {
    banStatus = aBanStatus;
  }

  private void setGuideStatus(GuideStatus aGuideStatus)
  {
    guideStatus = aGuideStatus;
  }

  private void setRequiredStatus(RequiredStatus aRequiredStatus)
  {
    requiredStatus = aRequiredStatus;
  }
  /* Code from template association_GetOne */
  public DiveSafe getDiveSafe()
  {
    return diveSafe;
  }
  /* Code from template association_GetOne */
  public Assignment getAssignment()
  {
    return assignment;
  }

  public boolean hasAssignment()
  {
    boolean has = assignment != null;
    return has;
  }
  /* Code from template association_GetMany */
  public ItemBooking getItemBooking(int index)
  {
    ItemBooking aItemBooking = itemBookings.get(index);
    return aItemBooking;
  }

  public List<ItemBooking> getItemBookings()
  {
    List<ItemBooking> newItemBookings = Collections.unmodifiableList(itemBookings);
    return newItemBookings;
  }

  public int numberOfItemBookings()
  {
    int number = itemBookings.size();
    return number;
  }

  public boolean hasItemBookings()
  {
    boolean has = itemBookings.size() > 0;
    return has;
  }

  public int indexOfItemBooking(ItemBooking aItemBooking)
  {
    int index = itemBookings.indexOf(aItemBooking);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDiveSafe(DiveSafe aDiveSafe)
  {
    boolean wasSet = false;
    if (aDiveSafe == null)
    {
      return wasSet;
    }

    DiveSafe existingDiveSafe = diveSafe;
    diveSafe = aDiveSafe;
    if (existingDiveSafe != null && !existingDiveSafe.equals(aDiveSafe))
    {
      existingDiveSafe.removeMember(this);
    }
    diveSafe.addMember(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAssignment(Assignment aNewAssignment)
  {
    boolean wasSet = false;
    if (assignment != null && !assignment.equals(aNewAssignment) && equals(assignment.getMember()))
    {
      //Unable to setAssignment, as existing assignment would become an orphan
      return wasSet;
    }

    assignment = aNewAssignment;
    Member anOldMember = aNewAssignment != null ? aNewAssignment.getMember() : null;

    if (!this.equals(anOldMember))
    {
      if (anOldMember != null)
      {
        anOldMember.assignment = null;
      }
      if (assignment != null)
      {
        assignment.setMember(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItemBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ItemBooking addItemBooking(int aQuantity, DiveSafe aDiveSafe, Item aItem)
  {
    return new ItemBooking(aQuantity, aDiveSafe, this, aItem);
  }

  public boolean addItemBooking(ItemBooking aItemBooking)
  {
    boolean wasAdded = false;
    if (itemBookings.contains(aItemBooking)) { return false; }
    Member existingMember = aItemBooking.getMember();
    boolean isNewMember = existingMember != null && !this.equals(existingMember);
    if (isNewMember)
    {
      aItemBooking.setMember(this);
    }
    else
    {
      itemBookings.add(aItemBooking);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItemBooking(ItemBooking aItemBooking)
  {
    boolean wasRemoved = false;
    //Unable to remove aItemBooking, as it must always have a member
    if (!this.equals(aItemBooking.getMember()))
    {
      itemBookings.remove(aItemBooking);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemBookingAt(ItemBooking aItemBooking, int index)
  {  
    boolean wasAdded = false;
    if(addItemBooking(aItemBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItemBookings()) { index = numberOfItemBookings() - 1; }
      itemBookings.remove(aItemBooking);
      itemBookings.add(index, aItemBooking);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemBookingAt(ItemBooking aItemBooking, int index)
  {
    boolean wasAdded = false;
    if(itemBookings.contains(aItemBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItemBookings()) { index = numberOfItemBookings() - 1; }
      itemBookings.remove(aItemBooking);
      itemBookings.add(index, aItemBooking);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemBookingAt(aItemBooking, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    DiveSafe placeholderDiveSafe = diveSafe;
    this.diveSafe = null;
    if(placeholderDiveSafe != null)
    {
      placeholderDiveSafe.removeMember(this);
    }
    Assignment existingAssignment = assignment;
    assignment = null;
    if (existingAssignment != null)
    {
      existingAssignment.delete();
    }
    for(int i=itemBookings.size(); i > 0; i--)
    {
      ItemBooking aItemBooking = itemBookings.get(i - 1);
      aItemBooking.delete();
    }
    super.delete();
  }

  // line 69 "../../../../../DiveSafe.ump"
   public static  Member getWithEmail(String email){
    if (User.getWithEmail(email) instanceof Member member) {
      return member;
    }
    return null;
  }

  // line 76 "../../../../../DiveSafe.ump"
   public static  boolean hasWithEmail(String email){
    return getWithEmail(email) != null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "numDays" + ":" + getNumDays()+ "," +
            "guideRequired" + ":" + getGuideRequired()+ "," +
            "hotelRequired" + ":" + getHotelRequired()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "diveSafe = "+(getDiveSafe()!=null?Integer.toHexString(System.identityHashCode(getDiveSafe())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignment = "+(getAssignment()!=null?Integer.toHexString(System.identityHashCode(getAssignment())):"null");
  }
}