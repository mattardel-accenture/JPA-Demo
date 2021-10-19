Feature: Add Book 2

    Scenario Outline: Correct data entered in input field
        Given User is on the Add Book page
        When User enters any data for <title> and <author>
        And User enters valid numeric data for <price>
        Then User is directed to the Books page upon selecting the active Submit button
        And The details for <title>, <author>, and <price> displays in the list as previously entered on the Add book page
        Examples:
            |   title      |   author   | price     |
            | "Fake Title1"| "John Doe" | "9.99"    |
            |       " "    |    " "     |  "100"    |
            |  "a"         |    " d"    |  "100.9"  |