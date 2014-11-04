Feature: Reteriving Location from store when that location not present

  Scenario Outline: Search Location by Location Id
    Given I made a rest call
    When I gave "<locationId>" as GET paramter
    And press submit
    Then I got response as "<message>"

    Examples: 
      | locationId | message |
      | 1234       | 404     |
      | 5678       | 404     |
