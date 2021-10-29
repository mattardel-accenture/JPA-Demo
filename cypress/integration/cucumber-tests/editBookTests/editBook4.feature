Feature: Edit Book 4

    Scenario Outline: Submit button disabled because invalid data entered in Price input field
        Given There is a book with title <title> and author <author> and price <price>
        And User is on the Edit book page for book with title <title> and author <author> and price <price>
        When User enters title as <updatedTitle>
        And User enters author as <updatedAuthor>
        And User enters price as <updatedPrice> as an invalid number
        Then Submit button is disabled
        Examples:
            | title        | author     | price   | updatedTitle | updatedAuthor     | updatedPrice   |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "notanumber"   |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "100.10.10"    |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "700.100"      |
            | "Fake Title" | "John Doe" | "9.99"  | "New Book"   | "Jonathan Buck"   | "9."           |