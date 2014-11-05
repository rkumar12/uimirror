@large
Feature: Searching location by longitude and latutide

  Background: 
    Given I made a rest call with "<latitude>", "<longitude>" as GET paramter
    And press submit

  Scenario Outline: valid longitude and latitude
    Then I got response as "<message>"

    Examples: 
      | latitude | longitude | message |
      | 1234     | 404       | 200     |
      | 5678     | 404       | 200     |

  Scenario Outline: invalid longitude and latitude
    Then I got response as "<message>"

    Examples: 
      | latitude | longitude | message |
      | 1234     | 404       | 200     |
      | 5678     | 404       | 200     |
