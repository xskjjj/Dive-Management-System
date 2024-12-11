Feature: Delete Equipment (p7)
  As an administrator, I want to delete existing equipment in the system so that I can restrict the equipment available to divers.

  Background: 
    Given the following DiveSafe system exists: (p7)
      | startDate  | numDays | priceOfGuidePerDay |
      | 2022-01-13 |      20 |                 50 |
    Given the following pieces of equipment exist in the system: (p7)
      | name       | weight | pricePerDay |
      | wetsuit    |    900 |          11 |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Given the following equipment bundles exist in the system: (p7)
      | name         | discount | items               | quantity |
      | small bundle |       10 | fins,dive boots     |      2,1 |
      | large bundle |       25 | fins,dive boots,BCD |    2,1,1 |

  Scenario: Successfully delete a piece of equipment
    When the administrator attempts to delete the piece of equipment in the system with name "wetsuit" (p7)
    Then the number of pieces of equipment in the system shall be "3" (p7)
    Then the piece of equipment with name "wetsuit", weight "900", and price per day "11" shall not exist in the system (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |

  Scenario: Delete equipment that has been requested by a member
    Given the following members exist in the system: (p7)
      | email             | password | name  | emergencyContact | numDays | guideRequired | hotelRequired | itemBookings | quantity |
      | member1@email.com | pass1    | Peter | (666)555-5555    |       1 | true          | true          | wetsuit,fins |      1,2 |
    When the administrator attempts to delete the piece of equipment in the system with name "wetsuit" (p7)
    Then the number of booked items for the member with email "member1@email.com" shall be "1" (p7)
    Then the member with email "member1@email.com" shall have a bookable item with name "fins" and quantity "2" (p7)
    Then the number of pieces of equipment in the system shall be "3" (p7)
    Then the piece of equipment with name "wetsuit", weight "900", and price per day "11" shall not exist in the system (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |

  Scenario Outline: Delete a piece of equipment that does not exist in the system
    When the administrator attempts to delete the piece of equipment in the system with name "<target>" (p7)
    Then the number of pieces of equipment in the system shall be "4" (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name       | weight | pricePerDay |
      | wetsuit    |    900 |          11 |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Then the number of equipment bundles in the system shall be "2" (p7)
    Then the following equipment bundles shall exist in the system: (p7)
      | name         | discount | items               | quantity |
      | small bundle |       10 | fins,dive boots     |      2,1 |
      | large bundle |       25 | fins,dive boots,BCD |    2,1,1 |

    Examples: 
      | target       |
      | harness      |
      | small bundle |

  Scenario: Unsuccessfully delete a piece of equipment that is in an existing bundle
    When the administrator attempts to delete the piece of equipment in the system with name "dive boots" (p7)
    Then the number of pieces of equipment in the system shall be "4" (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name       | weight | pricePerDay |
      | wetsuit    |    900 |          11 |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Then the number of equipment bundles in the system shall be "2" (p7)
    Then the following equipment bundles shall exist in the system: (p7)
      | name         | discount | items               | quantity |
      | small bundle |       10 | fins,dive boots     |      2,1 |
      | large bundle |       25 | fins,dive boots,BCD |    2,1,1 |
    Then the system shall raise the error "The piece of equipment is in a bundle and cannot be deleted" (p7)
