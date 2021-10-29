import { After, Given, When, Then, And } from 'cypress-cucumber-preprocessor/steps'
import { backendBaseURL, frontendBaseURL } from '../../hostUrl.js';

let titleToDelete = "";
let authorToDelete = "";
let priceToDelete = "";

Given('There is no book with title {string} and author {string} and price {string}', (title, author, price) => {
    //need to test if there's a book before we can find tr elements, cypress recommends using database for conditional tests like this
    cy.request('GET', backendBaseURL + '/books').then(
        (response) => {
            if(response.body.length > 0){
                    cy.visit(frontendBaseURL + '/books');
                    cy.bookExists(title, author, price);
                    cy.get('@bookExists').then(doesBookExist => {
                        cy.expect(doesBookExist).to.be.false;
                    })
            } else {
                //if no books in response then expect false
                cy.expect(false).to.be.false;
            }

        })

})
And('User is on the Add Book page', () => {
    cy.visit(frontendBaseURL + '/addbook');
})
When('User enters title as {string}', (title) => {
    cy.get('#title').type(title);
})
And('User enters author as {string}', (author) => {
    cy.get('#author').type(author);
})
And('User enters price as {string} as a valid number', (price) => {
    //use regex to evaluate string, can't use int because could be a double or int
    var regexp = /^[0-9]+([.][0-9]{1,2})?$/;
    var isANumber = regexp.test(price);
    cy.expect(isANumber).to.be.true;

    cy.get('#price').type(price);

})
And('User selects the active Submit button', (price) => {
    cy.get('#book-submit').should('be.enabled');
    cy.get('#book-submit').click();
})
Then('User is directed to the Books page', () => {
    cy.url().should('eq', frontendBaseURL + '/books');
})
And('There is a book with details for {string} and {string} and {string}', (titleExpected, authorExpected, priceExpected) => {
    //need to store these values to delete in after
    titleToDelete = titleExpected;
    authorToDelete = authorExpected;
    priceToDelete = priceExpected;

    cy.bookExists(titleExpected, authorExpected, priceExpected);
    cy.get('@bookExists').then(doesBookExist => {
        cy.expect(doesBookExist).to.be.true;
    })
})
After(() => {
    cy.visit(frontendBaseURL + '/books');
    cy.getIdFromBookPage(titleToDelete, authorToDelete, priceToDelete);
    cy.get('@foundId').then(id => {
        let url = backendBaseURL + '/books/' + id;
        cy.request('DELETE', url)
    })

})
