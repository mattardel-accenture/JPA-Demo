package com.cypress.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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



        String bookJson = "{\"title\":\"" + title + "\",\"author\":\"" + author + "\",\"price\":" + price + "}";

        MvcResult res = this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk()) //iscreated
                .andReturn();

        //check expectedBook was passed to repository
        Mockito.verify(bookRepository).save(expectedBook);
    }

}