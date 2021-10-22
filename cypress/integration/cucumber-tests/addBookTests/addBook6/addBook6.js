import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
Given('User is on the Add book page', () => {
    cy.visit('http://localhost:4200/addbook')

})
And('Submit button is disabled', () => {
    cy.get('#book-submit').should('be.disabled');
})

//the cypress typing function can't enter null so leave those fields blank
When('User selects the Submit button', () => {
    cy.get('#book-submit').click();
})

Then('User remains on the Add book page', () => {
    cy.url().should('eq', 'http://localhost:4200/editbook');
})