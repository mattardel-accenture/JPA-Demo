package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    public List<Book> getBooksService() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookByIdService(Long id){
        return(bookRepository.findById(id));
    }

    public void saveBookService(Book book){
        bookRepository.save(book);
    }

    public void deleteBookService(Long id){
        bookRepository.deleteById(id);
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }



}
