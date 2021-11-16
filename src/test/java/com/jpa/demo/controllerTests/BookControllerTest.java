package com.jpa.demo.controllerTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.demo.controller.BookController;
import com.jpa.demo.entity.Book;
import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.service.BookService;
import org.json.JSONObject;
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
import java.util.Optional;
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
    @Mock
    private BookService bookService;

    @Autowired
    @InjectMocks
    private BookController bookController;


    @BeforeEach
    private void controllerSetup(){
        //we do this in order to get the Mock service into the Mock controller when using MockMVC
        bookService.setBookRepository(bookRepository);
        bookController.setBookService(bookService);
    }


    @Test
    public void addBookTest() throws Exception {

        String title = "Charlotte's Web";
        String author = "E.B. White";
        Double price = 9.99;

        Book expectedBook = new Book(title, author, price);


        //turn book variables into JSON string to pass to POST req
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
        System.out.println("We are here1");
        Book b = new Book("48 Laws of Power", "Robert Greene", 15);
        List<Book> listedBooks = new ArrayList<>();
        listedBooks.add(b);
        listedBooks.add(new Book("Language of Letting Go", "Melody Beattie", 14));

        //creates a new implementation of findAll to return our local list of Books
        //since bookRepository doesn't actually exist when testing
        when(bookRepository.findAll()).thenReturn(listedBooks);


        MvcResult res = this.mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String bookString = res.getResponse().getContentAsString();

        //convert the response into books
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List <Book>> ref = new TypeReference <List <Book>>(){};
        List<Book> actualBooks = mapper.readValue(bookString, ref);

        assertEquals(listedBooks, actualBooks);

    }

    @Test
    public void listBookByIdTestValid() throws Exception {
        Book expected = new Book("48 Laws of Power", "Robert Greene", 15);
        expected.setId(1L);

        //creates a new implementation of findById to return our local book
        //since bookRepository doesn't actually exist when testing
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expected));

        MvcResult res = this.mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String bookString = res.getResponse().getContentAsString();

        //convert the response into a book
        ObjectMapper mapper = new ObjectMapper();
        Book actual = mapper.readValue(bookString, Book.class);

        assertEquals(expected, actual);

    }

    @Test
    public void listBookByIdTestInvalid() throws Exception {
        Book expected = new Book("48 Laws of Power", "Robert Greene", 15);
        expected.setId(1L);

        //creates a new implementation of findById to return our local book
        //since bookRepository doesn't actually exist when testing
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expected));

        MvcResult res = this.mockMvc.perform(get("/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();
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

    @Test
    public void editBookTest() throws Exception {

        String title = "Charlotte's Web";
        String author = "E.B. White";
        Double price = 9.99;

        String expectedTitle = "UpdatedBook";
        String expectedAuthor = "UpdatedAuthor";
        Double expectedPrice = 10.50;

        Book oldBook = new Book(title, author, price);
        oldBook.setId(1L);

        //create JSON string to pass into PUT req
        String bookUpdateJson = "{\"id\":1,\"title\":\"" + expectedTitle + "\",\"author\":\"" + expectedAuthor + "\",\"price\":" + expectedPrice + "}";

        //create new implementation of findById since bookRepository doesn't actually exist and can't have methods called on it
        when(bookRepository.findById(1L)).thenReturn(Optional.of(oldBook));

        MvcResult res = this.mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookUpdateJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        //turn response into JSON obj and parse it
        String bookString = res.getResponse().getContentAsString();
        JSONObject json = new JSONObject(bookString);

        String actualTitle = json.getString("title");
        String actualAuthor = json.getString("author");
        Double actualPrice = json.getDouble("price");

        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedAuthor, actualAuthor);
        assertEquals(expectedPrice, actualPrice);

    }

    @Test
    public void editBookNotFoundTest() throws Exception {
        String title = "Charlotte's Web";
        String author = "E.B. White";
        Double price = 9.99;

        String expectedTitle = "UpdatedBook";
        String expectedAuthor = "UpdatedAuthor";
        Double expectedPrice = 10.50;

        Book oldBook = new Book(title, author, price);
        oldBook.setId(1L);

        //create JSON string to pass into PUT req
        String bookUpdateJson = "{\"id\":1,\"title\":\"" + expectedTitle + "\",\"author\":\"" + expectedAuthor + "\",\"price\":" + expectedPrice + "}";

        //create new implementation of findById since bookRepository doesn't actually exist and can't have methods called on it
        when(bookRepository.findById(1L)).thenReturn(Optional.of(oldBook));

        //test that PUT req with non-existent ID returns HTTP is not found
        MvcResult res = this.mockMvc.perform(put("/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookUpdateJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();


    }


}