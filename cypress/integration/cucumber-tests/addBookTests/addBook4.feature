Feature: Add Book 4

    Scenario Outline: Submit button disabled because invalid data entered in Price input field
        Given User is on the Add Book page
        When User enters title as <title>
        And User enters author as <author>
        And User enters price as <price> as an invalid number
        Then Submit button is disabled
        Examples:
            | title        | author     | price         |
            | "Fake Title" | "John Doe" | "notanumber"  |
            | "Fake Title" | "John Doe" | "100.10.10"   |
            | "Fake Title" | "John Doe" | "700.100"     |
            | "Fake Title" | "John Doe" | "9."          |