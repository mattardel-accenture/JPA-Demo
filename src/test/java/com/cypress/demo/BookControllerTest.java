package com.cypress.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @Autowired
    @InjectMocks
    private BookController bookController;


    @BeforeEach
    private void controllerSetup(){
        //we do this in order to get the Mock service into the Mock controller when using MockMVC
        bookController.setBookRepository(bookRepository);
    }


    @Test
    public void addBookTest() throws Exception {

        String title = "Charlotte's Web";
        String author = "E.B. White";
        Double price = 9.99;

        Book expectedBook = new Book(title, author, price);



        String bookJson = "{\"id\":1,\"title\":\"" + title + "\",\"author\":\"" + author + "\",\"price\":" + price + "}";

        MvcResult res = this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andReturn();

        //check expectedBook was passed to repository
        Mockito.verify(bookRepository).save(expectedBook);
    }


    @Test
    public void listBookTest() throws Exception {
        Book b = new Book("48 Laws of Power", "Robert Greene", 15);
        List<Book> listedBooks = new ArrayList<>();
        listedBooks.add(b);
        listedBooks.add(new Book("Language of Letting Go", "Melody Beattie", 14));

        when(bookRepository.findAll()).thenReturn(listedBooks);
        MvcResult res = this.mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String bookString = res.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List <Book>> ref = new TypeReference <List <Book>>(){};
        List<Book> actualBooks = mapper.readValue(bookString, ref);

        assertEquals(listedBooks, actualBooks);

    }

    @Test
    public void deleteBookTest() throws Exception {

        MvcResult res = this.mockMvc.perform(delete("/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andReturn();

        Mockito.verify(bookRepository).deleteById(1L);
    }

}