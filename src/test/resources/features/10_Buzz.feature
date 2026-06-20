@Buzz @Regression
Feature: Buzz Social Feed Messaging

  Background:
    Given the user is logged in to the dashboard
    And the user navigates to the Buzz module

  Scenario: Post a message on Buzz and verify it in the feed
    When the user posts a dynamic message on the feed
    Then the message should be successfully displayed on the social timeline
