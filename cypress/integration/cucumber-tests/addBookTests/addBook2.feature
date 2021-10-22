Feature: Add Book 2

    Scenario Outline: Submit button enabled because user enters valid information
        Given User is on the Add book page
        When User enters title as <title>
        And User enters author as <author>
        And User enters price as <price> as a valid number
        Then Submit button is enabled
        Examples:
            |   title      |   author   | price     |
            | "Fake Title1"| "John Doe" | "9.99"    |
            |       " "    |    " "     |  "100"    |
            |  "a"         |    " d"    |  "100.9"  |