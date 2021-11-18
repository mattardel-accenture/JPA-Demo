package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.Book_;
import com.jpa.demo.entity.Genre_;
import com.jpa.demo.entity.Shelf_;
import com.jpa.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooksByWeirdCriteria() {
        return bookRepository.findAll(new Specification<Book>() {

            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate q = cb.like(root.get(Book_.author), "H%");
                q = cb.and(q, cb.like(root.join(Book_.genres).get(Genre_.description), "%IT%"));
                q = cb.and(q, cb.equal(root.join(Book_.shelf).get(Shelf_.room), "The Big Room"));
                return q;
            }
        });
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Boolean updateBook(Long id, Book book) {
        Optional<Book> foundBook = getBookById(id);
        //need to assert that foundBook isn't empty before we call get
        if (foundBook.isEmpty()){
            return Boolean.FALSE;
        }

        Book retrievedBook = foundBook.get();

        if (book.getShelf() != null) {
            retrievedBook.setShelf(book.getShelf());
        }
        if (book.getTitle() != null) {
            retrievedBook.setTitle(book.getTitle());
        }
        if (book.getPrice() != null) {
            retrievedBook.setPrice(book.getPrice());
        }
        if (book.getAuthor() != null) {
            retrievedBook.setAuthor(book.getAuthor());
        }
        if (book.getAuthor() != null) {
            retrievedBook.setPrice(book.getPrice());
        }

        saveBook(retrievedBook);
        return Boolean.TRUE;
    }
}
