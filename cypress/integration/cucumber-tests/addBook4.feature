Feature: Add Book 4

    Scenario Outline: No data entered in input field
        Given User is on the Add Book page
        When User leaves field blank for <title> and or <author> and or <price>
        Then Submit button is disabled
        And Submit button displays in grey
        Examples:
            | title      | author   | price |
            | "" | "John Doe" | 9.99  |