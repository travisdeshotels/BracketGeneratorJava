Feature: Command Line Test
  Check that the application works as expected once packaged

  Scenario: Simple input file
    Given I have the following data in my input file
      | Division | Type | Players              |
      | A        | SE   | Un,Deux,Trois,Quatre |
      | B        | SE   | Cinq,Six,Sept        |
    When I run the script in the command line
    And I read the output file
