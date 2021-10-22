import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook')
})

//the cypress typing function can't enter null so leave those fields blank
When('User leaves field blank for {string} and or {string} and or {string}', (title, author, price) => {
    if(title !== ""){
        cy.get('#title').type(title);
    }
    if(author !== ""){
        cy.get('#author').type(author);
    }
    if(price !== ""){
        cy.get('#price').type(price);
    }
})

Then('Submit button is disabled', () => {
    cy.get('#book-submit').should('be.disabled');
})