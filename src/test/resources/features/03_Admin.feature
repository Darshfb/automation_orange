@Admin @Regression
Feature: Admin User Management

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the Admin module

  Scenario: Search for existing user in Admin system
    When the user searches for the username "Admin"
    Then the user should see "Admin" in the search results list

  Scenario: Search for non-existing user
    When the user searches for a non-existing username "NonExistentUserXYZ"
    Then a "No Records Found" message should be displayed in the results table

  Scenario: Create a new system user linked to employee and verify search
    Given the user navigates to the PIM module
    And the user adds a new employee with a dynamically generated name
    And the user navigates to the Admin module
    When the user creates a system user linked to that employee
    Then the new user should be successfully found in the system users search

  Scenario: Edit an existing system user
    Given the user navigates to the PIM module
    And the user adds a new employee with a dynamically generated name
    And the user navigates to the Admin module
    And the user creates a system user linked to that employee
    When the user edits the system user status to "Disabled"
    Then the user should see the status "Disabled" in the search results list

  Scenario: Delete an existing system user
    Given the user navigates to the PIM module
    And the user adds a new employee with a dynamically generated name
    And the user navigates to the Admin module
    And the user creates a system user linked to that employee
    When the user deletes the created system user
    Then the system user search should return no results
