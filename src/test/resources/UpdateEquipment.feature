Feature: Update Equipment (p1)
  As an administrator, I want to update existing equipment in the system so that I can reflect changes to the equipment available to divers.

  Background: 
    Given the following DiveSafe system exists: (p1)
      | startDate  | numDays | priceOfGuidePerDay |
      | 2022-01-13 |      20 |                 50 |
    Given the following pieces of equipment exist in the system: (p1)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Given the following equipment bundles exist in the system: (p1)
      | name         | discount | items               | quantity |
      | small bundle |       10 | fins,dive boots     |      2,1 |
      | large bundle |       25 | fins,dive boots,BCD |    2,1,1 |

  Scenario Outline: Successfully update all information for a piece of equipment
    When the administator attempts to update the piece of equipment in the system with name "<oldName>" to have name "<newName>", weight "<newWeight>", and price per day "<newPricePerDay>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the piece of equipment with name "<oldName>", weight "<oldWeight>", and price per day "<oldPricePerDay>" shall not exist in the system (p1)
    Then the piece of equipment with name "<newName>", weight "<newWeight>", and price per day "<newPricePerDay>" shall exist in the system (p1)

    Examples: 
      | oldName    | oldWeight | oldPricePerDay | newName                | newWeight | newPricePerDay |
      | dive boots |       430 |             25 | lightweight dive boots |       280 |             35 |
      | fins       |        57 |              8 | ultra durable fins     |        63 |             15 |
      | dive boots |       430 |             25 | dive boots             |       390 |             25 |

  Scenario Outline: Unsuccessfully update a piece of equipment with invalid information
    When the administator attempts to update the piece of equipment in the system with name "dive boots" to have name "<newName>", weight "<newWeight>", and price per day "<newPricePerDay>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the following pieces of equipment shall exist in the system: (p1)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Then the system shall raise the error "<error>" (p1)

    Examples: 
      | newName                | newWeight | newPricePerDay | error                                                |
      | lightweight dive boots |         0 |             35 | The weight must be greater than 0                    |
      | lightweight dive boots |      -280 |             35 | The weight must be greater than 0                    |
      | lightweight dive boots |       280 |            -35 | The price per day must be greater than or equal to 0 |
      |                        |       280 |             35 | The name must not be empty                           |

  Scenario Outline: Unsuccessfully update a piece of equipment that does not exist in the system
    When the administator attempts to update the piece of equipment in the system with name "wetsuit" to have name "<newName>", weight "<newWeight>", and price per day "<newPricePerDay>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the following pieces of equipment shall exist in the system: (p1)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Then the system shall raise the error "The piece of equipment does not exist" (p1)

    Examples: 
      | newName            | newWeight | newPricePerDay |
      | adjustable wetsuit |      1050 |             14 |

  Scenario Outline: Unsuccessfully update a piece of equipment with a name that already exists in the system
    When the administator attempts to update the piece of equipment in the system with name "dive boots" to have name "<newName>", weight "<newWeight>", and price per day "<newPricePerDay>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the following pieces of equipment shall exist in the system: (p1)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Then the number of equipment bundles in the system shall be "2" (p1)
    Then the following equipment bundles shall exist in the system: (p1)
      | name         | discount | items               | quantity |
      | small bundle |       10 | fins,dive boots     |      2,1 |
      | large bundle |       25 | fins,dive boots,BCD |    2,1,1 |
    Then the system shall raise the error "<error>" (p1)

    Examples: 
      | newName      | newWeight | newPricePerDay | error                                                 |
      | fins         |       280 |             35 | The piece of equipment already exists                 |
      | small bundle |       280 |             35 | An equipment bundle with the same name already exists |
