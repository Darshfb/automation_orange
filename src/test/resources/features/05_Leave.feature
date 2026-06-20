@Leave @Regression
Feature: Leave Application Workflows

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the Leave module

  Scenario: Apply for leave with valid details
    When the user submits a leave application from "2026-10-10" to "2026-10-12" with comment "Vacation Request"
    Then the leave request should be successfully submitted
