package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.Shelf;
import com.jpa.demo.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void shouldGetAllBooks() {
        bookService.getBooks();
        Mockito.verify(bookRepository).findAll();
    }

    @Test
    public void shouldGetBookById() {
        Book sampleBook = new Book("title", "author", 15);
        sampleBook.setId(1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        Optional<Book> foundBook = bookService.getBookById(1L);
        Mockito.verify(bookRepository).findById(1L);
        Assertions.assertEquals(foundBook, Optional.of(sampleBook));
    }

    @Test
    public void shouldSaveBook() {
        Book sampleBook = new Book("title", "author", 15);
        bookService.saveBook(sampleBook);
        Mockito.verify(bookRepository).save(sampleBook);
    }

    @Test
    public void shouldDeleteBook() {
        bookService.deleteBook(1L);
        Mockito.verify(bookRepository).deleteById(1L);
    }

    @Test
    public void shouldUpdateBookIfFound() {
        Book foundBook = new Book("title", "author", 15);
        Book newBook = new Book();
        newBook.setShelf(new Shelf());
        newBook.setTitle("New Title");
        newBook.setAuthor("New Author");
        newBook.setPrice(20);
        Mockito.when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(foundBook));
        Boolean result = bookService.updateBook(1L, newBook);


        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUpdateBookNotFound() {
        Mockito.when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Boolean result = bookService.updateBook(1L, new Book());
        Assertions.assertFalse(result);
    }
}
