Feature: Edit Book 1

    Scenario Outline: Edit Book button redirects to Add book page with data auto-populated
        Given User is on the Books page
        And There exists a book with title <title> and author <author> and price <price>
        When User selects Edit book button for the book with title <title> and author <author> and price <price>
        Then User is directed to the Edit book page for the book with title <title> and author <author> and price <price>
        And Title is auto-populated with <title>
        And Author is auto-populated with <author>
        And Price is auto-populated with <price>
        Examples:
            | title        | author     | price   |
            | "Fake Title1"| "John Doe" | "9.99"  |
            | "Fake Title5"| "Jane Doe" | "199.99"|
            | "Fake"       | "Jim"      | "9"     |