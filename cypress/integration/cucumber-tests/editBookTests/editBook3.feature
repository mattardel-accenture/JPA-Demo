Feature: Edit Book 3

    Scenario Outline: Submit button enabled because user enters valid information
        Given There is a book with title <title> and author <author> and price <price>
        And User is on the Edit book page for book with title <title> and author <author> and price <price>
        When User enters title as <updatedTitle>
        And User enters author as <updatedAuthor>
        And User enters price as <updatedPrice> as a valid number
        And User selects the active Submit button
        Then User is directed to Books page
        And There is a book with details for <updatedTitle> and <updatedAuthor> and <updatedPrice>
        Examples:
            | title        | author     | price   | updatedTitle | updatedAuthor     | updatedPrice   |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "19.99"        |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "100.10"       |  
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "700.1"        |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "9"            |