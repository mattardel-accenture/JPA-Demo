package com.jpa.demo.controller;

import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.Shelf;
import com.jpa.demo.service.BookService;
import com.jpa.demo.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShelfController {


    private ShelfService shelfService;
    @Autowired
    private BookService bookService;

    @GetMapping("/shelves")
    public List<Shelf> getShelves() {
        return shelfService.getShelvesService();
    }

    @GetMapping("/shelves/{id}")
    public ResponseEntity<Shelf> getShelvesById(@PathVariable("id") Long id) {
        Optional<Shelf> foundShelf = shelfService.getShelfByIdService(id);
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
        shelfService.saveShelfService(shelf);
        return new ResponseEntity<Shelf>(shelf, HttpStatus.CREATED);
    }

    @DeleteMapping("/shelves/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteShelf(@PathVariable("id") Long id){
        shelfService.deleteShelfService(id);
    }

    @PutMapping("/shelves/{id}")
    public ResponseEntity<Shelf> editShelf(@PathVariable("id") Long id, @RequestBody Shelf updateRequest){
        Optional<Shelf> foundShelf = shelfService.getShelfByIdService(id);
        //need to assert that foundBook isn't empty before we call get
        if (foundShelf.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Shelf updatedShelf = foundShelf.get();

        updatedShelf.setLocation(updateRequest.getLocation());
//        updatedBook.setBooks(updateRequest.getBooks());

        shelfService.saveShelfService(updatedShelf);

        return new ResponseEntity<Shelf>(updatedShelf, HttpStatus.OK);
    }

    @Autowired
    public void setShelfService(ShelfService shelfService){
        this.shelfService = shelfService;
    }

}
