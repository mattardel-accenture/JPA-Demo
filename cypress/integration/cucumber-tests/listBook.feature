Feature: List Book

    Scenario Outline: Add books button redirects to addbook page
        Given User is on the Accenture Bookstore Homepage
        And <num> books exist within the database
        When User selects List Books button
        Then User is directed to the Books page
        And User can see list of <num> books on the screen
        Examples:
            | num |
            |  30  |