@Performance @Regression
Feature: Performance Tracker and KPIs

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the Performance module

  Scenario: Performance dashboard load validation
    Then the performance page header should be visible
