/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.divesafe.model;
import java.util.*;

// line 28 "../../../../../DiveSafe.ump"
// line 186 "../../../../../DiveSafe.ump"
public class Administrator extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Administrator Associations
  private DiveSafe diveSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Administrator(String aEmail, String aPassword, DiveSafe aDiveSafe)
  {
    super(aEmail, aPassword);
    boolean didAddDiveSafe = setDiveSafe(aDiveSafe);
    if (!didAddDiveSafe)
    {
      throw new RuntimeException("Unable to create administrator due to diveSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public DiveSafe getDiveSafe()
  {
    return diveSafe;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setDiveSafe(DiveSafe aNewDiveSafe)
  {
    boolean wasSet = false;
    if (aNewDiveSafe == null)
    {
      //Unable to setDiveSafe to null, as administrator must always be associated to a diveSafe
      return wasSet;
    }
    
    Administrator existingAdministrator = aNewDiveSafe.getAdministrator();
    if (existingAdministrator != null && !equals(existingAdministrator))
    {
      //Unable to setDiveSafe, the current diveSafe already has a administrator, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    DiveSafe anOldDiveSafe = diveSafe;
    diveSafe = aNewDiveSafe;
    diveSafe.setAdministrator(this);

    if (anOldDiveSafe != null)
    {
      anOldDiveSafe.setAdministrator(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    DiveSafe existingDiveSafe = diveSafe;
    diveSafe = null;
    if (existingDiveSafe != null)
    {
      existingDiveSafe.setAdministrator(null);
    }
    super.delete();
  }

}