Feature: Add Book 3

    Scenario Outline: Submit button enabled and user redirected to Books page upon selection
    Given User is on the Add Book page
    And There is no book with title of <title> and author of <author> and price <price>
    When User enters title as <title>
    And User enters author as <author>
    And User enters valid numeric data for <price>
    And User selects the active Submit button
    Then User is directed to the Books page
    And There is a book with title of <title> and author of <author> and price <price> in the list
    Examples:
        | title        | author     | price   |
        | "ashjkdhasjikdhaskjdssdddcdsddadshas"| "John Doe" | "9.99"  |