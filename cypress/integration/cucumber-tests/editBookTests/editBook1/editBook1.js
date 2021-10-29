import { Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
import {frontendBaseURL, backendBaseURL} from '../../hostUrl'

let titleToDelete = "";
let authorToDelete = "";
let priceToDelete = "";
let savedId = 0;

Given('User is on the Books page', () => {
    cy.visit(frontendBaseURL + '/books')
})

And('There exists a book with title {string} and author {string} and price {string}', (title, author, price) => {
    cy.get('a[routerlink*="addbook"]').click();
    cy.get('#title').type(title);
    cy.get('#author').type(author);
    cy.get('#price').type(price);
    cy.get('#book-submit').click();

    cy.bookExists(title, author, price);
    cy.get('@bookExists').then(doesBookExist => {
        cy.expect(doesBookExist).to.be.true;
    })

    titleToDelete = title;
    authorToDelete = author;
    priceToDelete = price;
})

When('User selects Edit book button for the book with title {string} and author {string} and price {string}', (title, author, price) => {
    
    cy.getIdFromBookPage(title, author, price);
    cy.get('@foundId').then(id => {
        let buttonSelector = 'a#' + id + '.btn.btn-info';
        cy.get(buttonSelector).click()
        savedId = id;
    })

})

Then('User is directed to the Edit book page for the book with title {string} and author {string} and price {string}', (title, author, price) => {
    cy.url().should('eq', frontendBaseURL + '/editbook/' + savedId);
})

And('Title is auto-populated with {string}', (title) => {
    cy.get('input[name="title"]').invoke('val').should('eq', title);
})
And('Author is auto-populated with {string}', (author) => {
    cy.get('input[name="author"]').invoke('val').should('eq', author);
})
And('Price is auto-populated with {string}', (price) => {
    cy.get('input[name="price"]').invoke('val').should('eq', price.toString());
})

