@Dashboard @Regression
Feature: Dashboard Interface and Navigation

  Background:
    Given the user is logged in to the dashboard

  Scenario: Verify side menu navigation sections are present
    Then the user should see the dashboard page title and main navigation menu

  Scenario: User logout
    When the user logs out from the application
    Then the user should be redirected back to the login page
