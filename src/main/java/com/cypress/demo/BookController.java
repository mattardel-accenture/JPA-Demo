package com.cypress.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return new ResponseEntity<Book>(book, HttpStatus.CREATED);
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


}
