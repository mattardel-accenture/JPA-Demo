import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
import { frontendBaseURL } from '../../hostUrl';

Given('User is on the Add book page', () => {
    cy.visit(frontendBaseURL + '/addbook')
})
When('User selects the Submit button it fails', () => {

    //the submit button should fail when clicked with the error below
    cy.on('fail', (error) => {
        expect(error.toString()).to.include('CypressError: Timed out retrying after 4050ms: `cy.click()` failed because this element is `disabled`:');
    }).end()

    cy.get('#book-submit').click();
})

