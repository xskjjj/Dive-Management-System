Feature: Add Equipment (p4)
  As an administrator, I want to add equipment in the system so that it can be rented to divers.

  Background: 
    Given the following DiveSafe system exists: (p4)
      | startDate  | numDays | priceOfGuidePerDay |
      | 2022-01-13 |      20 |                 50 |
    Given the following pieces of equipment exist in the system: (p4)
      | name       | weight | pricePerDay |
      | dive boots |    430 |          25 |
      | fins       |     57 |           8 |
      | BCD        |     85 |          17 |
    Given the following equipment bundles exist in the system: (p4)
      | name         | discount | items               | quantity |
      | small bundle |       10 | fins,dive boots     |      2,1 |
      | large bundle |       25 | fins,dive boots,BCD |    2,1,1 |

  Scenario Outline: Successfully add a piece of equipment
    When the administator attempts to add a new piece of equipment to the system with name "<name>", weight "<weight>", and price per day "<pricePerDay>" (p4)
    Then the number of pieces of equipment in the system shall be "4" (p4)
    Then the piece of equipment with name "<name>", weight "<weight>", and price per day "<pricePerDay>" shall exist in the system (p4)

    Examples: 
      | name                   | weight | pricePerDay |
      | lightweight dive boots |    280 |          35 |
      | durable fins           |     63 |          15 |

  Scenario Outline: Unsuccessfully add a piece of equipment with invalid information
    When the administator attempts to add a new piece of equipment to the system with name "<name>", weight "<weight>", and price per day "<pricePerDay>" (p4)
    Then the number of pieces of equipment in the system shall be "3" (p4)
    Then the piece of equipment with name "<name>", weight "<weight>", and price per day "<pricePerDay>" shall not exist in the system (p4)
    Then the system shall raise the error "<error>" (p4)

    Examples: 
      | name                   | weight | pricePerDay | error                                                |
      | lightweight dive boots |      0 |          35 | The weight must be greater than 0                    |
      | lightweight dive boots |   -280 |          35 | The weight must be greater than 0                    |
      | lightweight dive boots |    280 |         -35 | The price per day must be greater than or equal to 0 |
      |                        |    280 |          35 | The name must not be empty                           |

  Scenario Outline: Unsuccessfully add a piece of equipment that already exists in the system
    When the administator attempts to add a new piece of equipment to the system with name "<name>", weight "<weight>", and price per day "<pricePerDay>" (p4)
    Then the number of pieces of equipment in the system shall be "3" (p4)
    Then the piece of equipment with name "<name>", weight "<weight>", and price per day "<pricePerDay>" shall not exist in the system (p4)
    Then the system shall raise the error "<error>" (p4)

    Examples: 
      | name         | weight | pricePerDay | error                                 |
      | dive boots   |    280 |          35 | The piece of equipment already exists |
      | small bundle |    487 |          30 | The equipment bundle already exists   |
