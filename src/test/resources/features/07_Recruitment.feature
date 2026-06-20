@Recruitment @Regression
Feature: Recruitment Candidate Creation

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the Recruitment module

  Scenario: Create a new candidate with dynamic email
    When the user adds a new candidate with a dynamically generated name
    Then the candidate registration should complete successfully
