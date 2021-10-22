import { But, Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook')

})
When('User enters title as {string}', (title) => {
    cy.get('#title').type(title);
})
And('User enters author as {string}', (author) => {
    cy.get('#author').type(author);
})
And('User enters price as {string} as an invalid number', (price) => {
    cy.get('#price').type(price);
        //use regex to evaluate string, can't use int because could be a double or int
        var regexp = /^[0-9]*([.][0-9]{2})?$/;
        var isANumber = regexp.test(price);
        cy.expect(isANumber).to.be.false;
})
Then('Submit button is disabled', () => {
    cy.get('#book-submit').should('be.disabled');
})
