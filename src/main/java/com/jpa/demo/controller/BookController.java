package com.jpa.demo.controller;

import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.entity.Book;
import com.jpa.demo.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    //shelf repository

    private BookRepository bookRepository;
    @Autowired
    private ShelfRepository shelfRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){
        Optional<Book> foundBook = bookRepository.findById(id);
        //need to assert that foundBook isn't empty before we call get
        if(foundBook.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book updatedBook = foundBook.get();
        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
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
    public ResponseEntity<Book> editBook(@PathVariable("id") Long id, @RequestBody Book updateRequest) {

        Optional<Book> foundBook = bookRepository.findById(id);
        //need to assert that foundBook isn't empty before we call get
        if (foundBook.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book updatedBook = foundBook.get();

        if (updateRequest.getShelf() != null) {
            shelfRepository.save(updateRequest.getShelf());
        }

        if (updateRequest.getTitle() != null) {
            updatedBook.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getAuthor() != null) {
            updatedBook.setAuthor(updateRequest.getAuthor());
        }
        if (updateRequest.getAuthor() != null) {
            updatedBook.setPrice(updateRequest.getPrice());
        }
        if (updateRequest.getShelf() != null) {
            updatedBook.setShelf(updateRequest.getShelf());
        }

        bookRepository.save(updatedBook);

        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);

    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


}
