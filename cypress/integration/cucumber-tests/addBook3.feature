Feature: Add Book 3

    Scenario Outline: Incorrect data entered in input field
        Given User is on the Add Book page
        When User enters any data for <title> and <author>
        And User enters non-numeric data for <price>
        Then User remains on the Add book page upon selecting the Submit button
        And Submit button is disabled
        Examples:
            | title      | author   | price |
            | "Fake Title" | "John Doe" | "notanumber"  |