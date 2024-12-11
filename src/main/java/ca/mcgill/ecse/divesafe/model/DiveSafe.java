/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.divesafe.model;
import java.sql.Date;
import java.util.*;

// line 1 "../../../../../DiveSafePersistence.ump"
// line 6 "../../../../../DiveSafe.ump"
// line 165 "../../../../../DiveSafe.ump"
public class DiveSafe
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DiveSafe Attributes
  private Date startDate;
  private int numDays;
  private int priceOfGuidePerDay;

  //DiveSafe Associations
  private Administrator administrator;
  private List<Guide> guides;
  private List<Member> members;
  private List<ItemBooking> itemBookings;
  private List<Equipment> equipments;
  private List<EquipmentBundle> bundles;
  private List<BundleItem> bundleItems;
  private List<Hotel> hotels;
  private List<Assignment> assignments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DiveSafe(Date aStartDate, int aNumDays, int aPriceOfGuidePerDay)
  {
    startDate = aStartDate;
    numDays = aNumDays;
    priceOfGuidePerDay = aPriceOfGuidePerDay;
    guides = new ArrayList<Guide>();
    members = new ArrayList<Member>();
    itemBookings = new ArrayList<ItemBooking>();
    equipments = new ArrayList<Equipment>();
    bundles = new ArrayList<EquipmentBundle>();
    bundleItems = new ArrayList<BundleItem>();
    hotels = new ArrayList<Hotel>();
    assignments = new ArrayList<Assignment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumDays(int aNumDays)
  {
    boolean wasSet = false;
    numDays = aNumDays;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriceOfGuidePerDay(int aPriceOfGuidePerDay)
  {
    boolean wasSet = false;
    priceOfGuidePerDay = aPriceOfGuidePerDay;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public int getNumDays()
  {
    return numDays;
  }

  public int getPriceOfGuidePerDay()
  {
    return priceOfGuidePerDay;
  }
  /* Code from template association_GetOne */
  public Administrator getAdministrator()
  {
    return administrator;
  }

  public boolean hasAdministrator()
  {
    boolean has = administrator != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Guide getGuide(int index)
  {
    Guide aGuide = guides.get(index);
    return aGuide;
  }

  public List<Guide> getGuides()
  {
    List<Guide> newGuides = Collections.unmodifiableList(guides);
    return newGuides;
  }

  public int numberOfGuides()
  {
    int number = guides.size();
    return number;
  }

  public boolean hasGuides()
  {
    boolean has = guides.size() > 0;
    return has;
  }

  public int indexOfGuide(Guide aGuide)
  {
    int index = guides.indexOf(aGuide);
    return index;
  }
  /* Code from template association_GetMany */
  public Member getMember(int index)
  {
    Member aMember = members.get(index);
    return aMember;
  }

  public List<Member> getMembers()
  {
    List<Member> newMembers = Collections.unmodifiableList(members);
    return newMembers;
  }

  public int numberOfMembers()
  {
    int number = members.size();
    return number;
  }

  public boolean hasMembers()
  {
    boolean has = members.size() > 0;
    return has;
  }

  public int indexOfMember(Member aMember)
  {
    int index = members.indexOf(aMember);
    return index;
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
  /* Code from template association_GetMany */
  public Equipment getEquipment(int index)
  {
    Equipment aEquipment = equipments.get(index);
    return aEquipment;
  }

  public List<Equipment> getEquipments()
  {
    List<Equipment> newEquipments = Collections.unmodifiableList(equipments);
    return newEquipments;
  }

  public int numberOfEquipments()
  {
    int number = equipments.size();
    return number;
  }

  public boolean hasEquipments()
  {
    boolean has = equipments.size() > 0;
    return has;
  }

  public int indexOfEquipment(Equipment aEquipment)
  {
    int index = equipments.indexOf(aEquipment);
    return index;
  }
  /* Code from template association_GetMany */
  public EquipmentBundle getBundle(int index)
  {
    EquipmentBundle aBundle = bundles.get(index);
    return aBundle;
  }

  public List<EquipmentBundle> getBundles()
  {
    List<EquipmentBundle> newBundles = Collections.unmodifiableList(bundles);
    return newBundles;
  }

  public int numberOfBundles()
  {
    int number = bundles.size();
    return number;
  }

  public boolean hasBundles()
  {
    boolean has = bundles.size() > 0;
    return has;
  }

  public int indexOfBundle(EquipmentBundle aBundle)
  {
    int index = bundles.indexOf(aBundle);
    return index;
  }
  /* Code from template association_GetMany */
  public BundleItem getBundleItem(int index)
  {
    BundleItem aBundleItem = bundleItems.get(index);
    return aBundleItem;
  }

  public List<BundleItem> getBundleItems()
  {
    List<BundleItem> newBundleItems = Collections.unmodifiableList(bundleItems);
    return newBundleItems;
  }

  public int numberOfBundleItems()
  {
    int number = bundleItems.size();
    return number;
  }

  public boolean hasBundleItems()
  {
    boolean has = bundleItems.size() > 0;
    return has;
  }

  public int indexOfBundleItem(BundleItem aBundleItem)
  {
    int index = bundleItems.indexOf(aBundleItem);
    return index;
  }
  /* Code from template association_GetMany */
  public Hotel getHotel(int index)
  {
    Hotel aHotel = hotels.get(index);
    return aHotel;
  }

  public List<Hotel> getHotels()
  {
    List<Hotel> newHotels = Collections.unmodifiableList(hotels);
    return newHotels;
  }

  public int numberOfHotels()
  {
    int number = hotels.size();
    return number;
  }

  public boolean hasHotels()
  {
    boolean has = hotels.size() > 0;
    return has;
  }

  public int indexOfHotel(Hotel aHotel)
  {
    int index = hotels.indexOf(aHotel);
    return index;
  }
  /* Code from template association_GetMany */
  public Assignment getAssignment(int index)
  {
    Assignment aAssignment = assignments.get(index);
    return aAssignment;
  }

  public List<Assignment> getAssignments()
  {
    List<Assignment> newAssignments = Collections.unmodifiableList(assignments);
    return newAssignments;
  }

  public int numberOfAssignments()
  {
    int number = assignments.size();
    return number;
  }

  public boolean hasAssignments()
  {
    boolean has = assignments.size() > 0;
    return has;
  }

  public int indexOfAssignment(Assignment aAssignment)
  {
    int index = assignments.indexOf(aAssignment);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAdministrator(Administrator aNewAdministrator)
  {
    boolean wasSet = false;
    if (administrator != null && !administrator.equals(aNewAdministrator) && equals(administrator.getDiveSafe()))
    {
      //Unable to setAdministrator, as existing administrator would become an orphan
      return wasSet;
    }

    administrator = aNewAdministrator;
    DiveSafe anOldDiveSafe = aNewAdministrator != null ? aNewAdministrator.getDiveSafe() : null;

    if (!this.equals(anOldDiveSafe))
    {
      if (anOldDiveSafe != null)
      {
        anOldDiveSafe.administrator = null;
      }
      if (administrator != null)
      {
        administrator.setDiveSafe(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuides()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Guide addGuide(String aEmail, String aPassword, String aName, String aEmergencyContact)
  {
    return new Guide(aEmail, aPassword, aName, aEmergencyContact, this);
  }

  public boolean addGuide(Guide aGuide)
  {
    boolean wasAdded = false;
    if (guides.contains(aGuide)) { return false; }
    DiveSafe existingDiveSafe = aGuide.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aGuide.setDiveSafe(this);
    }
    else
    {
      guides.add(aGuide);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuide(Guide aGuide)
  {
    boolean wasRemoved = false;
    //Unable to remove aGuide, as it must always have a diveSafe
    if (!this.equals(aGuide.getDiveSafe()))
    {
      guides.remove(aGuide);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuideAt(Guide aGuide, int index)
  {  
    boolean wasAdded = false;
    if(addGuide(aGuide))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuides()) { index = numberOfGuides() - 1; }
      guides.remove(aGuide);
      guides.add(index, aGuide);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuideAt(Guide aGuide, int index)
  {
    boolean wasAdded = false;
    if(guides.contains(aGuide))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuides()) { index = numberOfGuides() - 1; }
      guides.remove(aGuide);
      guides.add(index, aGuide);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGuideAt(aGuide, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMembers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Member addMember(String aEmail, String aPassword, String aName, String aEmergencyContact, int aNumDays, boolean aGuideRequired, boolean aHotelRequired)
  {
    return new Member(aEmail, aPassword, aName, aEmergencyContact, aNumDays, aGuideRequired, aHotelRequired, this);
  }

  public boolean addMember(Member aMember)
  {
    boolean wasAdded = false;
    if (members.contains(aMember)) { return false; }
    DiveSafe existingDiveSafe = aMember.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aMember.setDiveSafe(this);
    }
    else
    {
      members.add(aMember);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMember(Member aMember)
  {
    boolean wasRemoved = false;
    //Unable to remove aMember, as it must always have a diveSafe
    if (!this.equals(aMember.getDiveSafe()))
    {
      members.remove(aMember);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMemberAt(Member aMember, int index)
  {  
    boolean wasAdded = false;
    if(addMember(aMember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMembers()) { index = numberOfMembers() - 1; }
      members.remove(aMember);
      members.add(index, aMember);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMemberAt(Member aMember, int index)
  {
    boolean wasAdded = false;
    if(members.contains(aMember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMembers()) { index = numberOfMembers() - 1; }
      members.remove(aMember);
      members.add(index, aMember);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMemberAt(aMember, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItemBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ItemBooking addItemBooking(int aQuantity, Member aMember, Item aItem)
  {
    return new ItemBooking(aQuantity, this, aMember, aItem);
  }

  public boolean addItemBooking(ItemBooking aItemBooking)
  {
    boolean wasAdded = false;
    if (itemBookings.contains(aItemBooking)) { return false; }
    DiveSafe existingDiveSafe = aItemBooking.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aItemBooking.setDiveSafe(this);
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
    //Unable to remove aItemBooking, as it must always have a diveSafe
    if (!this.equals(aItemBooking.getDiveSafe()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEquipments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Equipment addEquipment(String aName, int aWeight, int aPricePerDay)
  {
    return new Equipment(aName, aWeight, aPricePerDay, this);
  }

  public boolean addEquipment(Equipment aEquipment)
  {
    boolean wasAdded = false;
    if (equipments.contains(aEquipment)) { return false; }
    DiveSafe existingDiveSafe = aEquipment.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aEquipment.setDiveSafe(this);
    }
    else
    {
      equipments.add(aEquipment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEquipment(Equipment aEquipment)
  {
    boolean wasRemoved = false;
    //Unable to remove aEquipment, as it must always have a diveSafe
    if (!this.equals(aEquipment.getDiveSafe()))
    {
      equipments.remove(aEquipment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEquipmentAt(Equipment aEquipment, int index)
  {  
    boolean wasAdded = false;
    if(addEquipment(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipments()) { index = numberOfEquipments() - 1; }
      equipments.remove(aEquipment);
      equipments.add(index, aEquipment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEquipmentAt(Equipment aEquipment, int index)
  {
    boolean wasAdded = false;
    if(equipments.contains(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipments()) { index = numberOfEquipments() - 1; }
      equipments.remove(aEquipment);
      equipments.add(index, aEquipment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEquipmentAt(aEquipment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public EquipmentBundle addBundle(String aName, int aDiscount)
  {
    return new EquipmentBundle(aName, aDiscount, this);
  }

  public boolean addBundle(EquipmentBundle aBundle)
  {
    boolean wasAdded = false;
    if (bundles.contains(aBundle)) { return false; }
    DiveSafe existingDiveSafe = aBundle.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aBundle.setDiveSafe(this);
    }
    else
    {
      bundles.add(aBundle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundle(EquipmentBundle aBundle)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundle, as it must always have a diveSafe
    if (!this.equals(aBundle.getDiveSafe()))
    {
      bundles.remove(aBundle);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleAt(EquipmentBundle aBundle, int index)
  {  
    boolean wasAdded = false;
    if(addBundle(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleAt(EquipmentBundle aBundle, int index)
  {
    boolean wasAdded = false;
    if(bundles.contains(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleAt(aBundle, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundleItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BundleItem addBundleItem(int aQuantity, EquipmentBundle aBundle, Equipment aEquipment)
  {
    return new BundleItem(aQuantity, this, aBundle, aEquipment);
  }

  public boolean addBundleItem(BundleItem aBundleItem)
  {
    boolean wasAdded = false;
    if (bundleItems.contains(aBundleItem)) { return false; }
    DiveSafe existingDiveSafe = aBundleItem.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aBundleItem.setDiveSafe(this);
    }
    else
    {
      bundleItems.add(aBundleItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundleItem(BundleItem aBundleItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundleItem, as it must always have a diveSafe
    if (!this.equals(aBundleItem.getDiveSafe()))
    {
      bundleItems.remove(aBundleItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleItemAt(BundleItem aBundleItem, int index)
  {  
    boolean wasAdded = false;
    if(addBundleItem(aBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleItems()) { index = numberOfBundleItems() - 1; }
      bundleItems.remove(aBundleItem);
      bundleItems.add(index, aBundleItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleItemAt(BundleItem aBundleItem, int index)
  {
    boolean wasAdded = false;
    if(bundleItems.contains(aBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleItems()) { index = numberOfBundleItems() - 1; }
      bundleItems.remove(aBundleItem);
      bundleItems.add(index, aBundleItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleItemAt(aBundleItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHotels()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Hotel addHotel(String aName, String aAddress, int aRating, Hotel.HotelType aType)
  {
    return new Hotel(aName, aAddress, aRating, aType, this);
  }

  public boolean addHotel(Hotel aHotel)
  {
    boolean wasAdded = false;
    if (hotels.contains(aHotel)) { return false; }
    DiveSafe existingDiveSafe = aHotel.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aHotel.setDiveSafe(this);
    }
    else
    {
      hotels.add(aHotel);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHotel(Hotel aHotel)
  {
    boolean wasRemoved = false;
    //Unable to remove aHotel, as it must always have a diveSafe
    if (!this.equals(aHotel.getDiveSafe()))
    {
      hotels.remove(aHotel);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHotelAt(Hotel aHotel, int index)
  {  
    boolean wasAdded = false;
    if(addHotel(aHotel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHotels()) { index = numberOfHotels() - 1; }
      hotels.remove(aHotel);
      hotels.add(index, aHotel);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHotelAt(Hotel aHotel, int index)
  {
    boolean wasAdded = false;
    if(hotels.contains(aHotel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHotels()) { index = numberOfHotels() - 1; }
      hotels.remove(aHotel);
      hotels.add(index, aHotel);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHotelAt(aHotel, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Assignment addAssignment(int aStartDay, int aEndDay, Member aMember)
  {
    return new Assignment(aStartDay, aEndDay, aMember, this);
  }

  public boolean addAssignment(Assignment aAssignment)
  {
    boolean wasAdded = false;
    if (assignments.contains(aAssignment)) { return false; }
    DiveSafe existingDiveSafe = aAssignment.getDiveSafe();
    boolean isNewDiveSafe = existingDiveSafe != null && !this.equals(existingDiveSafe);
    if (isNewDiveSafe)
    {
      aAssignment.setDiveSafe(this);
    }
    else
    {
      assignments.add(aAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssignment(Assignment aAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAssignment, as it must always have a diveSafe
    if (!this.equals(aAssignment.getDiveSafe()))
    {
      assignments.remove(aAssignment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignmentAt(Assignment aAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addAssignment(aAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignments()) { index = numberOfAssignments() - 1; }
      assignments.remove(aAssignment);
      assignments.add(index, aAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignmentAt(Assignment aAssignment, int index)
  {
    boolean wasAdded = false;
    if(assignments.contains(aAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignments()) { index = numberOfAssignments() - 1; }
      assignments.remove(aAssignment);
      assignments.add(index, aAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignmentAt(aAssignment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Administrator existingAdministrator = administrator;
    administrator = null;
    if (existingAdministrator != null)
    {
      existingAdministrator.delete();
      existingAdministrator.setDiveSafe(null);
    }
    while (guides.size() > 0)
    {
      Guide aGuide = guides.get(guides.size() - 1);
      aGuide.delete();
      guides.remove(aGuide);
    }
    
    while (members.size() > 0)
    {
      Member aMember = members.get(members.size() - 1);
      aMember.delete();
      members.remove(aMember);
    }
    
    while (itemBookings.size() > 0)
    {
      ItemBooking aItemBooking = itemBookings.get(itemBookings.size() - 1);
      aItemBooking.delete();
      itemBookings.remove(aItemBooking);
    }
    
    while (equipments.size() > 0)
    {
      Equipment aEquipment = equipments.get(equipments.size() - 1);
      aEquipment.delete();
      equipments.remove(aEquipment);
    }
    
    while (bundles.size() > 0)
    {
      EquipmentBundle aBundle = bundles.get(bundles.size() - 1);
      aBundle.delete();
      bundles.remove(aBundle);
    }
    
    while (bundleItems.size() > 0)
    {
      BundleItem aBundleItem = bundleItems.get(bundleItems.size() - 1);
      aBundleItem.delete();
      bundleItems.remove(aBundleItem);
    }
    
    while (hotels.size() > 0)
    {
      Hotel aHotel = hotels.get(hotels.size() - 1);
      aHotel.delete();
      hotels.remove(aHotel);
    }
    
    while (assignments.size() > 0)
    {
      Assignment aAssignment = assignments.get(assignments.size() - 1);
      aAssignment.delete();
      assignments.remove(aAssignment);
    }
    
  }

  // line 3 "../../../../../DiveSafePersistence.ump"
   public void reinitialize(){
    User.reinitializeUniqueEmail(getAdministrator(), getGuides(), getMembers());
    Item.reinitializeUniqueName(getEquipments(), getBundles());
    Hotel.reinitializeUniqueName(getHotels());
  }


  public String toString()
  {
    return super.toString() + "["+
            "numDays" + ":" + getNumDays()+ "," +
            "priceOfGuidePerDay" + ":" + getPriceOfGuidePerDay()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "administrator = "+(getAdministrator()!=null?Integer.toHexString(System.identityHashCode(getAdministrator())):"null");
  }
}