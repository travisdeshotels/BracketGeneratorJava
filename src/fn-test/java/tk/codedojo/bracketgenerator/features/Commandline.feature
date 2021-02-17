Feature: Command Line Test
  Check that the application works as expected once packaged

  Scenario: Simple input file
    Given I have the following data in my input file
      | Division | Type | Players              |
      | A        | SE   | Un,Deux,Trois,Quatre |
      | B        | SE   | Cinq,Six,Sept        |
    When I run the script in the command line
    And I read the output file
    Then I verify that the data in Division A matches
      | Match Number | player1 | player 2 |
      | 1            | Un      | Deux     |
      | 2            | Trois   | Quatre   |
      | 5            | NULL    | NULL     |
    And I verify that the data in Division B matches
      | Match Number | player1 | player 2 |
      | 3            | Cinq    | Six      |
      | 4            | Sept    | NULL     |
      | 6            | NULL    | NULL     |
