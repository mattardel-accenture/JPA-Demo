package com.cypress.demo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void addBookTest() throws Exception {
        String title = "The Gunslinger";
        String author = "Stephen King";
        Double price = 12.99;

        Book a = new Book(title, author, price);
        assertNull(a.getId());
        Book b = bookRepository.save(a);

        assertNotNull(b.getId());

        Book c = bookRepository.getById(b.getId());

        assertEquals(a,c);
        assertTrue(bookRepository.existsById(b.getId()));

        bookRepository.delete(c);
        assertFalse(bookRepository.existsById(b.getId()));

    }
}