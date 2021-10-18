import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook')
})

//the type function can't enter null so leave those fields blank
When('User leaves field blank for {string} and or {string} and or {float}', (title, author, price) => {
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

//When button is "greyed out" the opacity is set to 0.65
And('Submit button displays in grey', () => {
    cy.get('#book-submit').should('have.css', 'opacity', '0.65')
})