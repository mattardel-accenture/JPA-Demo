Feature: Add Book 5

    Scenario Outline: Submit button disabled because no data entered in input field(s)
        Given User is on the Add Book page
        When User leaves field blank for <title> and or <author> and or <price>
        Then Submit button is disabled
        Examples:
            | title | author     | price   |
            | ""    | "John Doe" | "9.99"  |
            | "Book"|   ""       | "9.99"  |
            | "Book"|   "Author" |  ""     |
            | "Book"|   ""       |  ""     |
            | ""    |   "Author" |  ""     |
            | ""    |   ""       |  "10"   |
            | ""    |   ""       |  ""     |