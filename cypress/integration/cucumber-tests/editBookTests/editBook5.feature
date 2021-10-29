Feature: Edit Book 5

    Scenario Outline: No data entered in input field
        Given There is a book with title <title> and author <author> and price <price>
        And User is on the Edit book page for book with title <title> and author <author> and price <price> 
        When User leaves field blank for <updatedTitle> and or <updatedAuthor> and or <updatedPrice>
        Then Submit button is disabled
        Examples:
            | title        | author            | price          | updatedTitle | updatedAuthor     | updatedPrice   |
            | "Title Start"|   "Author Start"  |   "13.99"      | ""           | "John Doe"        | "9.99"         |
            | "Title Start"|   "Author Start"  |   "13.99"      | "Book"       |   ""              | "9.99"         |
            | "Title Start"|   "Author Start"  |   "13.99"      | "Book"       |   "Author"        |  ""            |
            | "Title Start"|   "Author Start"  |   "13.99"      | "Book"       |   ""              |  ""            |
            | "Title Start"|   "Author Start"  |   "13.99"      | ""           |   "Author"        |  ""            |
            | "Title Start"|   "Author Start"  |   "13.99"      | ""           |   ""              |  "10"          |
            | "Title Start"|   "Author Start"  |   "13.99"      | ""           |   ""              |  ""            |