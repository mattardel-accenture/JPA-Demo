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

//the cypress typing function can't enter null so leave those fields blank
When('User leaves field blank for {string} and or {string} and or {string}', (updatedTitle, updatedAuthor, updatedPrice) => {
    cy.get('#title').clear();
    cy.get('#author').clear();
    cy.get('#price').clear();

    if(updatedTitle !== ""){
        cy.get('#title').type(updatedTitle);
    }
    if(updatedAuthor !== ""){
        cy.get('#author').type(updatedAuthor);
    }
    if(updatedPrice !== ""){
        cy.get('#price').type(updatedPrice);
    }
})

Then('Submit button is disabled', () => {
    cy.get('#book-submit').should('be.disabled');
})

After(() => {
    cy.visit(frontendBaseURL + '/books');
    cy.getIdFromBookPage(titleToDelete, authorToDelete, priceToDelete);
    cy.get('@foundId').then(id => {
        let url = backendBaseURL + '/books/' + id;
        cy.request('DELETE', url)
    })

})
