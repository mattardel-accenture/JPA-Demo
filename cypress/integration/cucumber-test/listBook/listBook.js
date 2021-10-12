import { Given, When, Then } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the home page', () => {
    //cy.visit('http://localhost:4200')
})

When('User clicks the list books button', () => {
    //cy.findByText('List Books').click()
})

Then('User should be directed to the books page', () => {
    //cy.url().should('eq', '/books')
})
