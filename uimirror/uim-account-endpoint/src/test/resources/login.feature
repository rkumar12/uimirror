@large
Feature: Login

  Background: 
    Given I gave "<user_id>", "<password>"
    And I hit the Authenticate button

  Scenario Outline: Login success with otp screen
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
