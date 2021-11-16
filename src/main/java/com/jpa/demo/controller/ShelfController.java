package com.jpa.demo.controller;

import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.Shelf;
import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShelfController {

    //shelf repository

    private ShelfRepository shelfRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/shelves")
    public List<Shelf> getShelves() {
        return shelfRepository.findAll();
    }

    @GetMapping("/shelves/{id}")
    public ResponseEntity<Shelf> getShelvesById(@PathVariable("id") Long id) {
        Optional<Shelf> foundShelf = shelfRepository.findById(id);
        if(foundShelf.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Shelf updatedShelf = foundShelf.get();
        return new ResponseEntity<Shelf>(updatedShelf, HttpStatus.OK);
    }

    @PostMapping("/shelves")
    public ResponseEntity<Shelf> addShelf(@RequestBody Shelf shelf) {
//        if (!shelf.getBooks().isEmpty()) {
//            for (Book book : shelf.getBooks()) {
//                if (bookRepository.findById(book.getId()) != null) {
//
//                }
//                bookRepository.save(book);
//            }
//        }
        shelfRepository.save(shelf);
        return new ResponseEntity<Shelf>(shelf, HttpStatus.CREATED);
    }

    @DeleteMapping("/shelves/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteShelf(@PathVariable("id") Long id){
        shelfRepository.deleteById(id);
    }

    @PutMapping("/shelves/{id}")
    public ResponseEntity<Shelf> editShelf(@PathVariable("id") Long id, @RequestBody Shelf updateRequest){
        Optional<Shelf> foundShelf = shelfRepository.findById(id);
        //need to assert that foundBook isn't empty before we call get
        if (foundShelf.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Shelf updatedBook = foundShelf.get();

        updatedBook.setLocation(updateRequest.getLocation());
//        updatedBook.setBooks(updateRequest.getBooks());

        shelfRepository.save(updatedBook);

        return new ResponseEntity<Shelf>(updatedBook, HttpStatus.OK);
    }

    @Autowired
    public void setShelfRepository(ShelfRepository shelfRepository){
        this.shelfRepository = shelfRepository;
    }

}
