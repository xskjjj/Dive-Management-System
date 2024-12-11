Feature: Add/Update Hotel (p13)
  As an administrator, I wish to add and update Hotel information in the system.

  Background: 
    Given the following DiveSafe system exists: (p13)
      | startDate  | numDays | priceOfGuidePerDay |
      | 2022-03-13 |       7 |                100 |
    Given the following hotels exist in the system: (p13)
      | name       | address       | nrStars | type   |
      | Marriott   | 1 West Avenue |       5 | Villa  |
      | HolidayInn | 1 East Avenue |       4 | Hostel |

  Scenario Outline: Add a hotel successfully
    When the admin attempts to add a new hotel to the system with "<name>", "<address>", "<nrStars>", and "<type>" (p13)
    Then the hotel with "<name>", "<address>", "<nrStars>", and "<type>" shall now exist in the system (p13)
    Then the number of hotels in the system is 3 (p13)

    Examples: 
      | name      | address        | nrStars | type   |
      | Kempinski | 1 South Avenue |       5 | Resort |
      | Hilton    | 1 North Avenue |       4 | Resort |

  Scenario Outline: Add a hotel unsuccesfully
    When the admin attempts to add a new hotel to the system with "<name>", "<address>", "<nrStars>", and "<type>" (p13)
    Then the following "<error>" shall be raised (p13)
    Then the hotel with "<name>" shall "<target>" in the system (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | address         | nrStars | type  | error                                   | target    |
      |            | 4 Center Avenue |       3 | Villa | Name cannot be empty                    | not exist |
      | Hyaat      |                 |       4 | Villa | Address cannot be empty                 | not exist |
      | WesternInn | 4 Center Avenue |      -1 | Villa | Number of stars must be between 1 and 5 | not exist |
      | WesternInn | 4 Center Avenue |       0 | Villa | Number of stars must be between 1 and 5 | not exist |
      | WesternInn | 4 Center Avenue |       6 | Villa | Number of stars must be between 1 and 5 | not exist |
      | WesternInn | 4 Center Avenue |       1 | Motel | Hotel type Motel does not exist         | not exist |
      | Marriott   | 4 Center Avenue |       3 | Villa | Hotel already exists in the system      | exist     |
      | HolidayInn | 1 East Avenue   |       4 | Villa | Hotel already exists in the system      | exist     |

  Scenario Outline: Update a hotel successfully
    When the admin attempts to update hotel with "<name>" in the system to have a "<newName>", "<newAddress>", "<newNrStars>", and "<newType>" (p13)
    Then the hotel will be updated to have a "<newName>", "<newAddress>", "<newNrStars>", and "<newType>" (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | newName            | newAddress         | newNrStars | newType |
      | Marriott   | JW Marriot         | 100 Ave. Boulevard |          4 | Resort  |
      | HolidayInn | HolidayInn Express | 25 Prince St.      |          5 | Resort  |

  Scenario Outline: Update a hotel unsuccessfully
    When the admin attempts to update hotel with "<name>" in the system to have a "<newName>", "<newAddress>", "<newNrStars>", and "<newType>" (p13)
    Then the following "<error>" shall be raised (p13)
    Then the hotel will keep its "<name>", "<oldAddress>", "<oldNrStars>", and "<oldType>" (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | newName    | newAddress       | newNrStars | newType | error                                    | oldAddress    | oldNrStars | oldType |
      | Marriott   |            | 45 Center Avenue |          3 | Villa   | Name cannot be empty                     | 1 West Avenue |          5 | Villa   |
      | Marriott   | Marriott   |                  |          5 | Villa   | Address cannot be empty                  | 1 West Avenue |          5 | Villa   |
      | HolidayInn | HolidayInn | 55 West Avenue   |         -1 | Villa   | Number of stars must be between 1 and 5  | 1 East Avenue |          4 | Hostel  |
      | HolidayInn | HolidayInn | 55 West Avenue   |          0 | Villa   | Number of stars must be between 1 and 5  | 1 East Avenue |          4 | Hostel  |
      | HolidayInn | HolidayInn | 55 West Avenue   |          6 | Villa   | Number of stars must be between 1 and 5  | 1 East Avenue |          4 | Hostel  |
      | HolidayInn | HolidayInn | 55 West Avenue   |          1 | Motel   | Hotel type Motel does not exist          | 1 East Avenue |          4 | Hostel  |
      | Marriott   | HolidayInn | 1 West Avenue    |          5 | Villa   | New name already linked to another hotel | 1 West Avenue |          5 | Villa   |

  Scenario Outline: Update a hotel which does not exist in the system
    When the admin attempts to update hotel with "<name>" in the system to have a "<newName>", "<newAddress>", "<newNrStars>", and "<newType>" (p13)
    Then the following error "Hotel does not exist in the system" shall be raised (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | newName      | newAddress       | newNrStars | newType |
      | Carlton    | Carlton Ritz | 45 Center Avenue |          4 | Resort  |
      | WesternInn | WesternInn   | 54 Center Avenue |          5 | Resort  |
