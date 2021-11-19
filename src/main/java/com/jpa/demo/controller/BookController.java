package com.jpa.demo.controller;

import com.jpa.demo.entity.Book;
import com.jpa.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Optional<Book> foundBook = bookService.getBookById(id);
        if (foundBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book updatedBook = foundBook.get();
        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return new ResponseEntity<Book>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> editBook(@PathVariable("id") Long id, @RequestBody Book book) {
        Boolean successfulUpdate = bookService.updateBook(id, book);
        HttpStatus status = successfulUpdate ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Book>(book, status);
    }

    @GetMapping("/books/weirdcriteria")
    public List<Book> getBooksByWeirdCriteria() {
        return bookService.getBooksByWeirdCriteria();
    }
}
