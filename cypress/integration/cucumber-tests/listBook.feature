Feature: listBook
Scenario Outline: Show the list of books
Given User is at the home page
When User clicks the list books button
Then User is redirected to books page
Examples:
| username | password |
| Admin | admin123 |