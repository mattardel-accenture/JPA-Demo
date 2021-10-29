Cypress.Commands.add('bookExists', (title, author, price) => {
    cy.url().should('eq', 'http://localhost:4200/books');
    var titles = [];
    var authors = [];
    var prices = [];
    var found = false;

    //removes insignificant zeroes
    price = parseFloat(price);

    cy.get('table[id="Books"]').find('tr.book-element').each(($el) => {
        //push each title, author, price into respective arrays at same index
        titles.push($el.find('td[id="title"]').text());
        authors.push($el.find('td[id="author"]').text());
        prices.push($el.find('td[id="price"]').text());

    }).then(() =>{
         //each book element will occupy same array index, so check for title, author, price at each index
        //if they all match at same index, then wrap function will make the then callback eq true
        for(let i = 0; i < titles.length; i++){
            if(titles[i] == title && authors[i] == author && prices[i] == "$" + price){
                cy.wrap(true).as('bookExists')
                found = true;
            }
        }
        if(!found){
            cy.wrap(false).as('bookExists')
        }

    })


})



Cypress.Commands.add('getIdFromBookPage', (title, author, price) => {
    cy.url().should('eq', 'http://localhost:4200/books');
    var titles = [];
    var authors = [];
    var prices = [];
    var ids = [];
    var found = false;
    price = parseFloat(price);


    cy.get('table[id="Books"]').find('tr.book-element').each(($el) => {
        titles.push($el.find('td[id="title"]').text());
        authors.push($el.find('td[id="author"]').text());
        prices.push($el.find('td[id="price"]').text());
        ids.push($el.find('a').attr('id'));
    }).then(() =>{

        for(let i = 0; i < titles.length; i++){

            if(titles[i] == title && authors[i] == author && prices[i] == "$" + price){
                cy.wrap(ids[i]).as('foundId');
                found = true;
            }
        }
        if(!found){
            cy.wrap(-1).as('foundId');
        }

    })

})