import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook')
})
When('User enters any data for {string} and {string}', (title, author) => {
    cy.get('#title').type(title);
    cy.get('#author').type(author);
})


And('User enters non-numeric data for {string}', (price) => {
    cy.get('#price').type(price);
})


Then('User remains on the Add book page upon selecting the Submit button', () => {
    cy.get('#book-submit').should('be.disabled');
})


And('Submit button is disabled', () => {

})