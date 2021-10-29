import { Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
import {frontendBaseURL, backendBaseURL} from '../../hostUrl'

Given('User is on the Accenture Bookstore Homepage', () => {
    cy.visit(frontendBaseURL)
})
When('User selects Add Book button', () => {
    cy.get('a[routerlink*="addbook"]').click()
})
Then('User is directed to the Add book page', () => {
    cy.url().should('eq', frontendBaseURL + '/addbook')
})