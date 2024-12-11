/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.divesafe.model;
import java.util.*;

// line 22 "../../../../../DiveSafePersistence.ump"
// line 86 "../../../../../DiveSafe.ump"
// line 213 "../../../../../DiveSafe.ump"
public abstract class Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Item> itemsByName = new HashMap<String, Item>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String name;

  //Item Associations
  private List<ItemBooking> itemBookings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(String aName)
  {
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    itemBookings = new ArrayList<ItemBooking>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      itemsByName.remove(anOldName);
    }
    itemsByName.put(aName, this);
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static Item getWithName(String aName)
  {
    return itemsByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItemBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ItemBooking addItemBooking(int aQuantity, DiveSafe aDiveSafe, Member aMember)
  {
    return new ItemBooking(aQuantity, aDiveSafe, aMember, this);
  }

  public boolean addItemBooking(ItemBooking aItemBooking)
  {
    boolean wasAdded = false;
    if (itemBookings.contains(aItemBooking)) { return false; }
    Item existingItem = aItemBooking.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aItemBooking.setItem(this);
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
    //Unable to remove aItemBooking, as it must always have a item
    if (!this.equals(aItemBooking.getItem()))
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
    itemsByName.remove(getName());
    for(int i=itemBookings.size(); i > 0; i--)
    {
      ItemBooking aItemBooking = itemBookings.get(i - 1);
      aItemBooking.delete();
    }
  }

  // line 24 "../../../../../DiveSafePersistence.ump"
   public static  void reinitializeUniqueName(List<Equipment> equipments, List<EquipmentBundle> bundles){
    itemsByName.clear();
    for (var e : equipments) {
      itemsByName.put(e.getName(), e);
    }
    for (var bundle : bundles) {
      itemsByName.put(bundle.getName(), bundle);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}