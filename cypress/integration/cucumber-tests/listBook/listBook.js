import { Given, When, Then } from 'cypress-cucumber-preprocessor/steps'
Given('User is at the home page', () => {
cy.visit('http://localhost:4200/')
})
When('User clicks the list books button', () => {
    cy.get('a[routerlink*="books"]').click()
})
Then('User is redirected to books page', () => {
    cy.url().should('eq', 'http://localhost:4200/books')
})