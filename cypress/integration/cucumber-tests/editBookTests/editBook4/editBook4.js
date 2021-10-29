import { After, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
import {frontendBaseURL, backendBaseURL} from '../../hostUrl'

let titleToDelete = "";
let authorToDelete = "";
let priceToDelete = "";

Given('There is a book with title {string} and author {string} and price {string}', (title, author, price) => {
    cy.visit(frontendBaseURL);
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


And('User is on the Edit book page for book with title {string} and author {string} and price {string}', (title, author, price) => {
    cy.getIdFromBookPage(title, author, price);
    cy.get('@foundId').then(id => {
        cy.visit(frontendBaseURL + "/editbook/" + id);
    }) 
})

When('User enters title as {string}', (updatedTitle) => {
    cy.get('#title').clear();
    cy.get('#title').type(updatedTitle);
})
And('User enters author as {string}', (updatedAuthor) => {
    cy.get('#author').clear();
    cy.get('#author').type(updatedAuthor);
})
And('User enters price as {string} as an invalid number', (updatedPrice) => {
    //use regex to evaluate string, can't use int because could be a double or int
    var regexp = /^[0-9]+([.][0-9]{1,2})?$/;
    var isANumber = regexp.test(updatedPrice);
    cy.expect(isANumber).to.be.false;

    cy.get('#price').clear();
    cy.get('#price').type(updatedPrice);
})

Then('Submit button is disabled', () => {
    cy.get('#book-submit').should('not.be.enabled');
})

After(() => {
    cy.visit(frontendBaseURL + '/books');
    cy.getIdFromBookPage(titleToDelete, authorToDelete, priceToDelete);
    cy.get('@foundId').then(id => {
        let url = backendBaseURL + '/books/' + id;
        cy.request('DELETE', url)
    })

})
