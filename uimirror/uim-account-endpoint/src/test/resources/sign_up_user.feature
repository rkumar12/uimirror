@large
Feature: Registering user

  Background: 
    Given I have an apikey "1234"
    Given I enter "<first_name>", "<last_name>", "<email>", "<password>", "gender", "dob"
  	Given I click on accept policy
  	Given I click on signup

  Scenario Outline: valid registeration
    Then I got response code "<response_code>"

    Examples: 
      | first_name  | last_name   |    email                  |  password      | gender  |    dob        | response_code |
      | Jayaram     | Pradhan     | jayaramimca@gmail.com     |  Omm!jay200    |  M      |  1988-03-18   |   200         |
      | Jayaram     | Pradhan     | jayaramimca1@gmail.com    |  Omm!jay200    |  M      |  1988-03-18   |   200         |

  Scenario Outline: Account Already Exists
    Then I got response code "<response_code>"

    Examples: 
      | first_name  | last_name   |    email                  |  password      | gender  |    dob        | response_code |
      | Jayaram     | Pradhan     | jayaramimca@gmail.com     |  Omm!jay200    |  M      |  1988-03-18   |   302         |
      | Jayaram     | Pradhan     | jayaramimca1@gmail.com    |  Omm!jay200    |  M      |  1988-03-18   |   302         |

  Scenario Outline: Invalid Input
    Then I got response code "<response_code>"

    Examples: 
      | first_name  | last_name   |    email                  |  password      | gender  |    dob        | response_code |
      | $''$        | Pradhan     | jayaramimca               |  Omm!jay200    |  M      |  1988-03-18   |   406         |
      | @test       | Pradhan     | jayaramimca1@gmail.com    |  Omm!jay200    |  M      |  1988-03-18   |   406         |
