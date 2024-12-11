Feature: Add equipment bundle (p2)
  As the administrator, I wish to add equipment bundles to the system so that divers can rent them.

  Background: 
    Given the following DiveSafe system exists: (p2)
      | startDate  | numDays | priceOfGuidePerDay |
      | 2022-01-13 |      20 |                 50 |
    Given the following equipment exists in the system: (p2)
      | name       | weight | pricePerDay |
      | fins       |   3540 |           5 |
      | dive boots |    907 |          50 |
      | BCD        |    340 |         100 |
      | bag        |    850 |          10 |

  Scenario Outline: Add an equipment bundle successfully
    When the administrator attempts to add an equipment bundle with name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p2)
    Then the number of equipment bundles in the system shall be "1" (p2)
    Then the equipment bundle "<name>" shall exist in the system (p2)
    Then the equipment bundle "<name>" shall contain the items "<items>" with quantities "<quantities>" (p2)
    Then the equipment bundle "<name>" shall have a discount of "<discount>" (p2)

    Examples: 
      | name         | discount | items               | quantities |
      | small bundle |       10 | fins,dive boots     |        2,1 |
      | large bundle |       25 | fins,dive boots,BCD |      2,1,1 |

  Scenario Outline: Add an equipment bundle with invalid parameters
    When the administrator attempts to add an equipment bundle with name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p2)
    Then the number of equipment bundles in the system shall be "0" (p2)
    Then the error "<error>" shall be raised (p2)

    Examples: 
      | name            | discount | items               | quantities | error                                                                  |
      | clothing bundle |       10 | bag,coat            |        1,1 | Equipment coat does not exist                                          |
      | empty bundle    |        0 |                     |            | Equipment bundle must contain at least two distinct types of equipment |
      | tiny bundle     |       25 | fins                |          2 | Equipment bundle must contain at least two distinct types of equipment |
      | small bundle    |       15 | fins,fins           |        2,1 | Equipment bundle must contain at least two distinct types of equipment |
      | empty bundle    |        0 | fins,BCD            |       -1,1 | Each bundle item must have quantity greater than or equal to 1         |
      | empty bundle    |        0 | fins,dive boots     |        1,0 | Each bundle item must have quantity greater than or equal to 1         |
      | small bundle    |       -1 | fins,dive boots     |        2,1 | Discount must be at least 0                                            |
      | large bundle    |      101 | fins,dive boots,BCD |      2,1,1 | Discount must be no more than 100                                      |
      |                 |        0 | fins,dive boots     |        2,1 | Equipment bundle name cannot be empty                                  |

  Scenario Outline: Add a duplicate equipment bundle
    Given the following equipment bundles exist in the system: (p2)
      | name         | discount | items           | quantities |
      | small bundle |       10 | fins,dive boots |        2,1 |
    When the administrator attempts to add an equipment bundle with name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p2)
    Then the number of equipment bundles in the system shall be "1" (p2)
    Then the equipment bundle "small bundle" shall exist in the system (p2)
    Then the equipment bundle "small bundle" shall contain the items "fins,dive boots" with quantities "2,1" (p2)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p2)
    Then the error "<error>" shall be raised (p2)

    Examples: 
      | name         | discount | items               | quantities | error                                              |
      | small bundle |       25 | fins,dive boots,bag |      3,2,1 | A bookable item called small bundle already exists |
      | dive boots   |       30 | dive boots,fins     |        1,1 | A bookable item called dive boots already exists   |
