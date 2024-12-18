/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.divesafe.model;

// line 124 "../../../../../DiveSafe.ump"
// line 228 "../../../../../DiveSafe.ump"
public class BundleItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BundleItem Attributes
  private int quantity;

  //BundleItem Associations
  private DiveSafe diveSafe;
  private EquipmentBundle bundle;
  private Equipment equipment;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetBundle;
  private boolean canSetEquipment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BundleItem(int aQuantity, DiveSafe aDiveSafe, EquipmentBundle aBundle, Equipment aEquipment)
  {
    cachedHashCode = -1;
    canSetBundle = true;
    canSetEquipment = true;
    quantity = aQuantity;
    boolean didAddDiveSafe = setDiveSafe(aDiveSafe);
    if (!didAddDiveSafe)
    {
      throw new RuntimeException("Unable to create bundleItem due to diveSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBundle = setBundle(aBundle);
    if (!didAddBundle)
    {
      throw new RuntimeException("Unable to create bundleItem due to bundle. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddEquipment = setEquipment(aEquipment);
    if (!didAddEquipment)
    {
      throw new RuntimeException("Unable to create bundleItem due to equipment. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  public DiveSafe getDiveSafe()
  {
    return diveSafe;
  }
  /* Code from template association_GetOne */
  public EquipmentBundle getBundle()
  {
    return bundle;
  }
  /* Code from template association_GetOne */
  public Equipment getEquipment()
  {
    return equipment;
  }
  /* Code from template association_SetOneToManyAssociationClass */
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
      existingDiveSafe.removeBundleItem(this);
    }
    if (!diveSafe.addBundleItem(this))
    {
      diveSafe = existingDiveSafe;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setBundle(EquipmentBundle aBundle)
  {
    boolean wasSet = false;
    if (!canSetBundle) { return false; }
    if (aBundle == null)
    {
      return wasSet;
    }

    EquipmentBundle existingBundle = bundle;
    bundle = aBundle;
    if (existingBundle != null && !existingBundle.equals(aBundle))
    {
      existingBundle.removeBundleItem(this);
    }
    if (!bundle.addBundleItem(this))
    {
      bundle = existingBundle;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setEquipment(Equipment aEquipment)
  {
    boolean wasSet = false;
    if (!canSetEquipment) { return false; }
    if (aEquipment == null)
    {
      return wasSet;
    }

    Equipment existingEquipment = equipment;
    equipment = aEquipment;
    if (existingEquipment != null && !existingEquipment.equals(aEquipment))
    {
      existingEquipment.removeBundleItem(this);
    }
    if (!equipment.addBundleItem(this))
    {
      equipment = existingEquipment;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    BundleItem compareTo = (BundleItem)obj;
  
    if (getBundle() == null && compareTo.getBundle() != null)
    {
      return false;
    }
    else if (getBundle() != null && !getBundle().equals(compareTo.getBundle()))
    {
      return false;
    }

    if (getEquipment() == null && compareTo.getEquipment() != null)
    {
      return false;
    }
    else if (getEquipment() != null && !getEquipment().equals(compareTo.getEquipment()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getBundle() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getBundle().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getEquipment() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getEquipment().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetBundle = false;
    canSetEquipment = false;
    return cachedHashCode;
  }

  public void delete()
  {
    DiveSafe placeholderDiveSafe = diveSafe;
    this.diveSafe = null;
    if(placeholderDiveSafe != null)
    {
      placeholderDiveSafe.removeBundleItem(this);
    }
    EquipmentBundle placeholderBundle = bundle;
    this.bundle = null;
    if(placeholderBundle != null)
    {
      placeholderBundle.removeBundleItem(this);
    }
    Equipment placeholderEquipment = equipment;
    this.equipment = null;
    if(placeholderEquipment != null)
    {
      placeholderEquipment.removeBundleItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "diveSafe = "+(getDiveSafe()!=null?Integer.toHexString(System.identityHashCode(getDiveSafe())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bundle = "+(getBundle()!=null?Integer.toHexString(System.identityHashCode(getBundle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "equipment = "+(getEquipment()!=null?Integer.toHexString(System.identityHashCode(getEquipment())):"null");
  }
}