package com.cypress.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @PostMapping("/books")
    void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

//    @GetMapping("/favorites")
//    public String favorites(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "favorites";
//    }
//
//    @GetMapping("/bookstore")
//    public String bookstore(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "bookstore";
//    }

}
