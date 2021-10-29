import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
import {frontendBaseURL, backendBaseURL} from '../../hostUrl'

Given('User is on the Add book page', () => {
    cy.visit(frontendBaseURL + '/addbook');

})
When('User enters title as {string}', (title) => {
    cy.get('#title').type(title);
})
And('User enters author as {string}', (author) => {
    cy.get('#author').type(author);
})
And('User enters price as {string} as a valid number', (price) => {
    //use regex to evaluate string, can't use int because could be a double or int
    var regexp = /^[0-9]+([.][0-9]{1,2})?$/;
    var isANumber = regexp.test(price);
    cy.expect(isANumber).to.be.true;

    cy.get('#price').type(price);
})
And('{string} is entered as a valid number', (price) => {
    //use regex to evaluate string, can't use int because could be a double or int
    var regexp = /^[0-9]*(\.[0-9]{0,2})?$/;
    var isANumber = regexp.test(price);
    cy.expect(isANumber).to.be.true;

})
Then('Submit button is enabled', () => {
    cy.get('#book-submit').should('be.enabled');
})