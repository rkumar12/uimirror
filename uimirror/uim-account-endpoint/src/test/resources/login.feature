@medium
Feature: Searching location by location id

  Background: 
    Given I made a rest call with "<locationId>" as GET parameter
    And I hit the url

  Scenario Outline: Search Location with an invalid Location Id
    Then I got response as "<code>"

    Examples: 
      | locationId | code |
      | 1234       | 204  |
      | 5678       | 204  |

  Scenario Outline: Search Location with a valid Location Id
    Then I got response as "<code>"

    Examples: 
      | locationId | code |
      | 1234       | 200  |
      | 5678       | 200  |

  Scenario Outline: Search Location with a valid Location Id and expand result
  	And I gave "<expand>" option in the GET parameter
    Then I got response as "<code>"

    Examples: 
      | locationId | expand | code |
      | 1234       | Y      | 200  |
      | 5678       | Y      | 200  |
