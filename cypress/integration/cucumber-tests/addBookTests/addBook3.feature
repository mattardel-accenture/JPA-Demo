Feature: Add Book 3

    Scenario Outline: Submit button enabled and user redirected to Books page upon selection
    Given There is no book with title <title> and author <author> and price <price>
    And User is on the Add Book page
    When User enters title as <title>
    And User enters author as <author>
    And User enters price as <price> as a valid number
    And User selects the active Submit button
    Then User is directed to the Books page
    And There is a book with details for <title> and <author> and <price>
    Examples:
        | title        | author     | price   |
        | "Test Book"  | "John Doe" | "9.99"  |
        | "new book"   | "Jane Doe" | "10.00" |
        | "Old Yeller" | "Jim Doe"  | "100.0" |