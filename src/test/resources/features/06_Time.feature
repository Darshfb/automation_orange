@Time @Regression
Feature: Time and Attendance Module Workflows

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the Time module

  Scenario: Navigation and timesheet lookup verification
    Then the time header breadcrumb should be displayed
