@MyInfo @Regression
Feature: My Info Personal Details

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the My Info module

  Scenario: Verify My Info screen is loaded successfully
    Then the personal details header section should be visible
