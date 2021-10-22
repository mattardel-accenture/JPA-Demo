import { Before, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

//delete all pre-existing books
Before(() => {
    cy.request('GET', 'http://localhost:8080/books').then(
        (response) => {
            for(let i = 0; i < response.body.length; i++){
                let url = 'http://localhost:8080/books/' + response.body[i].id;
                cy.request('DELETE', url);
            }
        })
})


Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook')
})

When('User enters any data for {string} and {string}', (title, author) => {
    cy.get('#title').type(title);
    cy.get('#author').type(author);
})


And('User enters valid numeric data for {string}', (price) => {
    cy.get('#price').type(price);
})

Then('User is directed to the Books page upon selecting the active Submit button', () => {
    cy.get('#book-submit').should('not.be.disabled');
    cy.get('#book-submit').click();
    cy.url().should('eq', 'http://localhost:4200/books')
})

And('The details for {string}, {string}, and {string} displays in the list as previously entered on the Add book page', (title, author, price) => {
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').find('td[id="title"]').should('have.text', title);
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').find('td[id="author"]').should('have.text', author);
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').find('td[id="price"]').should('have.text', '$' + price);
})