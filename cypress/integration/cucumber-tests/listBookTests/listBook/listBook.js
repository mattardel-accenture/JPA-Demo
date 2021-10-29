import { Given, When, Then, And, After, Before } from 'cypress-cucumber-preprocessor/steps'
import { backendBaseURL, frontendBaseURL } from '../../hostUrl';

let idDeletionArray = [];
let numCurrentBooks = 0;


//get number of pre-existing books in system
Before(() => {
    cy.request('GET', backendBaseURL + '/books').then(
        (response) => {
            numCurrentBooks = response.body.length;
        })
})


Given('User is on the Accenture Bookstore Homepage', () => {
    cy.visit(frontendBaseURL)
})

//create number of books specified in feature file and verify creation
And("{int} books exist within the database", (num) => {
    //when testing for 0 delete all pre-existing books
    if(num == 0){
        cy.request('GET', backendBaseURL + '/books').then(
            (response) => {
                for(let i = 0; i < response.body.length; i++){
                    let url = backendBaseURL + '/books/' +response.body[i].id;
                    cy.request('DELETE', url);
                }
            })
    }

    //create specified number of new books
    for(let i = 0; i < num; i++){
        let title = "The Longest Series of Books Volume " + i;
        let json = { title: title, author: 'John Doe', price: 19.99};
        cy.request('POST', backendBaseURL + '/books', json).then(
        (response) => {
            //pass each created Book's id into an array for deletion
            idDeletionArray.push(response.body.id);
            expect(response.body).to.have.property('title', title)
        })
    }
})


When('User selects List Books button', () => {
    cy.get('a[routerlink*="books"]').click()
})

Then('User is directed to the Books page', () => {
    cy.url().should('eq', frontendBaseURL + '/books')
})


//verify that new books have been added based on book-element html class
And('User can see list of {int} books on the screen', (num) => {
    //when testing with 0 there should only be one row which is header
    if(num == 0){
        cy.get('table[id="Books"]').find('tr').should('have.length', num+1);
    } else {
    //when testing other than 0 add all pre-existing books to the count so number of rows check is correct
    cy.get('table[id="Books"]').find('tr').should('have.class', 'book-element').should('have.length', num+1+numCurrentBooks);
    }
})


After(() => {
    //delete request to delete all books except pre-existing
    for(let i = 0; i < idDeletionArray.length; i++){
        let url = backendBaseURL + '/books/' + idDeletionArray[i];
        cy.request('DELETE', url)
    }

    //reset deletion array so no duplicate delete attempted
    idDeletionArray = [];
})