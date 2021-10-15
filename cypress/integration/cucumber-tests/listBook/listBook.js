import { Given, When, Then, And, After, Before } from 'cypress-cucumber-preprocessor/steps'

let idDeletionArray = [];
let idCounter = 0;
let numCurrentBooks = 0;

Before(() => {
    cy.request('GET', 'http://localhost:8080/books').then(
        (response) => {
            //pass each created Book's id into an array for deletion
            numCurrentBooks = response.body.length;
        })
})


Given('User is on the Accenture Bookstore Homepage', () => {
    cy.visit('http://localhost:4200/')
})

//create number of books specified in feature file and verify creation
And("{int} books exist within the database", (num) => {

    for(let i = 0; i < num; i++){
        let title = "The Longest Series of Books Volume " + i;
        let json = { title: title, author: 'John Doe', price: 19.99};
        cy.request('POST', 'http://localhost:8080/books', json).then(
        (response) => {
            //pass each created Book's id into an array for deletion
            idDeletionArray[idCounter] = response.body.id;
            idCounter++;
            //push instead ^
            expect(response.body).to.have.property('title', title)
        })
    }
})

When('User selects List Books button', () => {
    cy.get('a[routerlink*="books"]').click()
})

Then('User is directed to the Books page', () => {
    cy.url().should('eq', 'http://localhost:4200/books')
})

//check number of rows in the table + 1 for the description row + number of preexisting books
And('User can see list of {int} books on the screen', (num) => {
    //class for tr
    cy.get('table[id="Books"]').find('tr').should('have.length', num+1+numCurrentBooks)
})

After(() => {
    //get request to list all books
    for(let i = 0; i < idCounter; i++){
        let url = 'http://localhost:8080/books/' + idDeletionArray[i];
        cy.request('DELETE', url)

    }
})