Feature: Update Equipment Bundle (p8)
  As an administrator, I wish to update existing equipment bundles so that I can keep divers up to date.

  Background: 
    Given the following DiveSafe system exists: (p8)
      | startDate  | numDays | priceOfGuidePerDay |
      | 2022-01-13 |      20 |                 50 |
    Given the following equipment exists in the system: (p8)
      | name       | weight | pricePerDay |
      | fins       |   3540 |           5 |
      | dive boots |    907 |          50 |
      | BCD        |    340 |         100 |
      | bag        |    850 |          10 |
      | backpack   |    500 |          20 |
    Given the following equipment bundles exist in the system: (p8)
      | name         | discount | items               | quantities |
      | small bundle |       10 | fins,dive boots     |        2,1 |
      | large bundle |       25 | fins,dive boots,BCD |      2,1,1 |

  Scenario Outline: Successfully update an equipment bundle without changing name
    When the administrator attempts to update the equipment bundle "<name>" to have name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the equipment bundle "<name>" shall contain the items "<items>" with quantities "<quantities>" (p8)
    Then the equipment bundle "<name>" shall have a discount of "<discount>" (p8)

    Examples: 
      | name         | discount | items                        | quantities |
      | small bundle |       20 | backpack,fins,dive boots     |      1,2,1 |
      | large bundle |       50 | backpack,fins,dive boots,BCD |    1,2,1,1 |

  Scenario Outline: Successfully update an equipment bundle, including changing name
    When the administrator attempts to update the equipment bundle "<oldName>" to have name "<newName>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the equipment bundle "<oldName>" shall not exist in the system (p8)
    Then the equipment bundle "<newName>" shall contain the items "<items>" with quantities "<quantities>" (p8)
    Then the equipment bundle "<newName>" shall have a discount of "<discount>" (p8)

    Examples: 
      | oldName      | newName              | discount | items                        | quantities |
      | small bundle | small bundle special |       20 | backpack,fins,dive boots     |      1,2,1 |
      | large bundle | large bundle special |       50 | backpack,fins,dive boots,BCD |    1,2,1,1 |

  Scenario Outline: Update an equipment bundle with invalid parameters without changing name
    When the administrator attempts to update the equipment bundle "small bundle" to have name "small bundle", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the number of pieces of equipment in the system shall be "5" (p8)
    Then the equipment bundle "small bundle" shall contain the items "fins,dive boots" with quantities "2,1" (p8)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p8)
    Then the error "<error>" shall be raised (p8)

    Examples: 
      | discount | items               | quantities | error                                                                  |
      |       35 | bag,coat            |        1,1 | Equipment coat does not exist                                          |
      |        0 |                     |            | Equipment bundle must contain at least two distinct types of equipment |
      |       25 | fins                |          2 | Equipment bundle must contain at least two distinct types of equipment |
      |       15 | fins,fins           |        2,1 | Equipment bundle must contain at least two distinct types of equipment |
      |        0 | fins,BCD            |       -1,1 | Each bundle item must have quantity greater than or equal to 1         |
      |        0 | fins,dive boots     |        1,0 | Each bundle item must have quantity greater than or equal to 1         |
      |       -1 | fins,BCD            |        2,1 | Discount must be at least 0                                            |
      |      101 | fins,dive boots,BCD |      2,1,1 | Discount must be no more than 100                                      |

  Scenario Outline: Update an equipment bundle with invalid parameters, including changing name
    When the administrator attempts to update the equipment bundle "small bundle" to have name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the number of pieces of equipment in the system shall be "5" (p8)
    Then the equipment bundle "<name>" shall not exist in the system (p8)
    Then the equipment bundle "small bundle" shall contain the items "fins,dive boots" with quantities "2,1" (p8)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p8)
    Then the error "<error>" shall be raised (p8)

    Examples: 
      | name                 | discount | items               | quantities | error                                                                  |
      | clothing bundle      |       35 | bag,coat            |        1,1 | Equipment coat does not exist                                          |
      | empty bundle         |        0 |                     |            | Equipment bundle must contain at least two distinct types of equipment |
      | tiny bundle          |       25 | fins                |          2 | Equipment bundle must contain at least two distinct types of equipment |
      | small bundle special |       15 | fins,fins           |        2,1 | Equipment bundle must contain at least two distinct types of equipment |
      | empty bundle         |        0 | fins,BCD            |       -1,1 | Each bundle item must have quantity greater than or equal to 1         |
      | empty bundle         |        0 | fins,dive boots     |        1,0 | Each bundle item must have quantity greater than or equal to 1         |
      | small bundle special |       -1 | fins,dive boots     |        2,1 | Discount must be at least 0                                            |
      | big bundle           |      101 | fins,dive boots,BCD |      2,1,1 | Discount must be no more than 100                                      |
      | dive boots           |       20 | dive boots,fins     |        1,1 | A bookable item called dive boots already exists                       |
      |                      |        0 | fins,dive boots     |        1,1 | Equipment bundle name cannot be empty                                  |

  Scenario: Update an equipment bundle using a name that already exists
    When the administrator attempts to update the equipment bundle "small bundle" to have name "large bundle", discount "30", items "bag,backpack,BCD", and quantities "1,1,1" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the equipment bundle "large bundle" shall contain the items "fins,dive boots,BCD" with quantities "2,1,1" (p8)
    Then the equipment bundle "large bundle" shall have a discount of "25" (p8)
    Then the equipment bundle "small bundle" shall contain the items "fins,dive boots" with quantities "2,1" (p8)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p8)
    Then the error "A bookable item called large bundle already exists" shall be raised (p8)

  Scenario Outline: Update an equipment bundle that does not exist
    When the administrator attempts to update the equipment bundle "<oldName>" to have name "<newName>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the number of pieces of equipment in the system shall be "5" (p8)
    Then the equipment bundle "<name>" shall not exist in the system (p8)
    Then the error "<error>" shall be raised (p8)

    Examples: 
      | oldName    | newName         | discount | items        | quantities | error                                      |
      | new bundle | clothing bundle |       10 | bag,backpack |        1,1 | Equipment bundle new bundle does not exist |
      | fins       | clothing bundle |       10 | bag,backpack |        1,1 | Equipment bundle fins does not exist       |
