import { Given, When, Then, And, After } from 'cypress-cucumber-preprocessor/steps'


Given('User is on the Accenture Bookstore Homepage', () => {
    cy.visit('http://localhost:4200/')
})

//create number of books specified in feature file and verify creation
And("{int} books exist within the database", (num) => {

    for(let i = 0; i < num; i++){
        cy.request('POST', 'http://localhost:8080/books', { title: '1984', author: 'George Orwell', price: 19.99 }).then(
        (response) => {
        expect(response.body).to.have.property('title', '1984')
        })
    }
})

When('User selects List Books button', () => {
    cy.get('a[routerlink*="books"]').click()
})

Then('User is directed to the Books page', () => {
    cy.url().should('eq', 'http://localhost:4200/books')
})

//check number of rows in the table + 1 for the description row
And('User can see list of {int} books on the screen', (num) => {
    cy.get('table').find('tr').should('have.length', num+1)
})

After(() => {
    cy.request('DELETE', 'http://localhost:8080/books')
})