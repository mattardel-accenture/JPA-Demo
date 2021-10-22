package com.cypress.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return new ResponseEntity<Book>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id){
        bookRepository.deleteById(id);
    }

    @PutMapping("/books/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Book> editBook(@PathVariable("id") Long id, @RequestBody Book updateRequest) {

        Optional<Book> foundBook = bookRepository.findById(id);

        if(foundBook.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book updatedBook = foundBook.get();

        updatedBook.setTitle(updateRequest.getTitle());
        updatedBook.setAuthor(updateRequest.getAuthor());
        updatedBook.setPrice(updateRequest.getPrice());

        bookRepository.save(updatedBook);

        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);

    }



    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


}
