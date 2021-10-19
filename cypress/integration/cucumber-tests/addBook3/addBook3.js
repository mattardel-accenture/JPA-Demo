import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook')
})

When('User enters any valid data for {string} and {string}', (title, author) => {
    cy.get('#title').type(title);
    cy.get('#author').type(author);
})

And('User enters invalid data for {string}', (price) => {
    cy.get('#price').type(price);
})


Then('Submit button is disabled', () => {
    cy.get('#book-submit').should('be.disabled');
})


//When button is "greyed out" the opacity is set to 0.65
And('Submit button displays in grey', () => {
    cy.get('#book-submit').should('have.css', 'opacity', '0.65')
})