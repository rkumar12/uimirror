Feature: Searching location by longitude and latutide

  Background: 
    Given I made a rest call
    And I gave "<longitude>", "<latitude>" as GET paramter
    And press submit

  Scenario Outline: Search Location by longitude and latitude
    Then I got response as "<message>"

    Examples: 
      | locationId | message |
      | 1234       | 404     |
      | 5678       | 404     |
