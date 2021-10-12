Feature: listBook

  Scenario: List all books
    Given User is on the home page
    When User clicks the list books button
    Then User should be directed to the books page