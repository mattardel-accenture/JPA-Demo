import { After, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'

Given('User is on the Add Book page', () => {
    cy.visit('http://localhost:4200/addbook');
})
And('There is no book with title of {string} and author of {string} and price {string}', (title, author, price, id) => {
    let doesBookExist = false;
    cy.request('GET', 'http://localhost:8080/books').then(
        (response) => {
            for(let i = 0; i < response.body.length; i++){
                if((response.body[i].title == title) && (response.body[i].author == author) && (response.body[i].price == price)){
                    //if all fields are the same then throw an error
                    doesBookExist = true;
                    cy.expect(doesBookExist).to.be.false;
                    break;
                }
            }
        })
        cy.expect(doesBookExist).to.be.false;
})
When('User enters title as {string}', (title) => {
    cy.get('#title').type(title);
})
And('User enters author as {string}', (author) => {
    cy.get('#author').type(author);
})
And('User enters valid numeric data for {string}', (price) => {
    cy.get('#price').type(price);

    //use regex to evaluate string, can't use int because could be a double or int
    var regexp = /^[0-9]*([.][0-9]{2})?$/;
    var isANumber = regexp.test(price);
    cy.expect(isANumber).to.be.true;
})
And('User selects the active Submit button', (price) => {
    cy.get('#book-submit').should('be.enabled');
    cy.get('#book-submit').click();
})
Then('User is directed to the Books page', () => {
    cy.url().should('eq', 'http://localhost:4200/books');
})
And('There is a book with title of {string} and author of {string} and price {string} in the list', (title, author, price) => {
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').find('td[id="title"]').contains(title);
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').find('td[id="author"]').contains(author);
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').find('td[id="price"]').contains(price);
})
