@PIM @Regression
Feature: PIM Employee Management CRUD Operations

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the PIM module

  Scenario: Create a new employee and verify creation
    When the user adds a new employee with a dynamically generated name
    Then the employee should be successfully found in the employee list search

  Scenario: Edit an existing employee
    Given the user adds a new employee with a dynamically generated name
    And the employee should be successfully found in the employee list search
    When the user edits the employee middle name to "QAUpdated"
    Then the employee list should display the updated middle name

  Scenario: Delete an existing employee
    Given the user adds a new employee with a dynamically generated name
    And the employee should be successfully found in the employee list search
    When the user deletes the created employee
    Then the employee search should return no results
