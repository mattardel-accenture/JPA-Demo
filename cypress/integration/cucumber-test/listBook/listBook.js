import { Given, When, Then } from 'cypress-cucumber-preprocessor/steps'

Given('User is at the home page', () => {
    //cy.visit('http://localhost:4200')
})
When('User clicks the list books button', () => {
    //cy.findByText('List Books').click()
})
Then('User is directed to the books page', () => {
    //cy.url().should('eq', '/books')
})
