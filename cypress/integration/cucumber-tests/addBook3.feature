Feature: Add Book 3

    Scenario Outline: Incorrect data entered in input field
        Given User is on the Add Book page
        When User enters any valid data for <title> and <author>
        And User enters invalid data for <price>
        Then Submit button is disabled
        And Submit button displays in grey
        Examples:
            | title        | author     | price         |
            | "Fake Title" | "John Doe" | "notanumber"  |
            | "Fake Title" | "John Doe" | "100.10.10"   |
            | "Fake Title" | "John Doe" | "700.100"     |
            | "Fake Title" | "John Doe" | "9."          |