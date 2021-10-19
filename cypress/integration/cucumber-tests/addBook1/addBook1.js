import { Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Accenture Bookstore Homepage', () => {
    cy.visit('http://localhost:4200/')
})
When('User selects Add Book button', () => {
    cy.get('a[routerlink*="addbook"]').click()
})
Then('User is directed to the Add book page', () => {
    cy.url().should('eq', 'http://localhost:4200/addbook')
})