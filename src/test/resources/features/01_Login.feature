@Authentication
Feature: User Authentication

  Background:
    Given the user is on the OrangeHRM login page

  @Smoke @Regression
  Scenario: Successful login with valid credentials
    When the user enters the configured admin username and password
    And clicks the login button
    Then the user should be redirected to the dashboard page

  @Regression
  Scenario Outline: Failed login with invalid credentials
    When the user enters username "<username>" and password "<password>"
    And clicks the login button
    Then an error message "Invalid credentials" should be displayed

    Examples:
      | username | password |
      | Admin    | wrong123 |
      | Invalid  | admin123 |
      | guest    | guest123 |

  @Regression @EdgeCase
  Scenario Outline: Validation error on empty fields
    When the user enters username "<username>" and password "<password>"
    And clicks the login button
    Then a field validation message "Required" should be displayed under the missing field

    Examples:
      | username | password |
      |          | admin123 |
      | Admin    |          |
      |          |          |
