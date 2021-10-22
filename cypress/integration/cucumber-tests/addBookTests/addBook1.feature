Feature: Add Book 1

    Scenario: Add Book button redirects to Add book page
        Given User is on the Accenture Bookstore Homepage
        When User selects Add Book button
        Then User is directed to the Add book page
