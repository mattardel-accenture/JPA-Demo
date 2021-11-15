package com.jpa.demo.shelf;

import com.jpa.demo.book.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShelfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ShelfRepository shelfRepository;

    @Autowired
    @InjectMocks
    private ShelfController shelfController;


    @BeforeEach
    private void controllerSetup(){
        //we do this in order to get the Mock service into the Mock controller when using MockMVC
        shelfController.setShelfRepository(shelfRepository);
    }


    @Test
    public void addShelfTest() throws Exception {

        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);
        a.setId(1L);
        b.setId(2L);

        List<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);

        Shelf newShelf = new Shelf(books, 5);
        newShelf.setId(1L);




        //turn shelf into JSON string to pass to POST req
        String bookJson = "{\"id\":1,\"books\":[{\"id\":1,\"title\":\"Charlotte\'s Web\",\"author\":\"E.B. White\",\"price\":9.99},{\"id\":2,\"title\":\"Matilda\",\"author\":\"Roald Dahl\",\"price\":19.99}],\"size\":5}";

        MvcResult res = this.mockMvc.perform(post("/shelves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .characterEncoding("utf-8"))
                .andReturn();

        System.out.println("HERE" + res.getResponse().getContentAsString());
        //check expectedBook was passed to repository
        Mockito.verify(shelfRepository).save(newShelf);
    }


    @Test
    public void listShelfTest() throws Exception {
        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);

        List<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);

        Shelf newShelf = new Shelf(books, 5);
        Shelf newShelfOther = new Shelf(books, 10);
        List<Shelf> shelfListExpected = new ArrayList<>();

        shelfListExpected.add(newShelf);
        shelfListExpected.add(newShelfOther);

        when(shelfRepository.findAll()).thenReturn(shelfListExpected);

        MvcResult res = this.mockMvc.perform(get("/shelves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String shelfString = res.getResponse().getContentAsString();
        System.out.println(shelfString);

        //convert the response into books
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List <Shelf>> ref = new TypeReference <List <Shelf>>(){};
        List<Shelf> shelfListActual = mapper.readValue(shelfString, ref);

        assertEquals(shelfListExpected, shelfListActual);
    }

    @Test
    public void listShelfByIdTestValid() throws Exception {

        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);

        List<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);
        a.setId(1L);
        b.setId(2L);

        Shelf expected = new Shelf(books, 5);
        expected.setId(1L);

        when(shelfRepository.findById(1L)).thenReturn(Optional.of(expected));

        MvcResult res = this.mockMvc.perform(get("/shelves/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String shelfString = res.getResponse().getContentAsString();

        //convert the response into a shelf
        ObjectMapper mapper = new ObjectMapper();
        Shelf actual = mapper.readValue(shelfString, Shelf.class);

        assertEquals(expected, actual);

    }

    @Test
    public void listShelfByIdTestInvalid() throws Exception {

        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);

        List<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);
        a.setId(1L);
        b.setId(2L);

        Shelf expected = new Shelf(books, 5);
        expected.setId(1L);

        when(shelfRepository.findById(1L)).thenReturn(Optional.of(expected));

        MvcResult res = this.mockMvc.perform(get("/shelves/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteShelfTest() throws Exception {
        MvcResult res = this.mockMvc.perform(delete("/shelves/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(shelfRepository).deleteById(1L);
    }

    @Test
    public void editShelfTestValidId() throws Exception {
        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);
        a.setId(1L);
        b.setId(2L);

        List<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);

        Shelf oldShelf = new Shelf(books, 5);
        oldShelf.setId(1L);
        when(shelfRepository.findById(1L)).thenReturn(Optional.of(oldShelf));
        System.out.println(oldShelf.getSize());

        //turn shelf into JSON string to pass to POST req
        String bookJson = "{\"id\":1,\"books\":[{\"id\":1,\"title\":\"Charlotte\'s Web\",\"author\":\"E.B. White\",\"price\":9.99}],\"size\":10}";

        MvcResult res = this.mockMvc.perform(put("/shelves/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();


        String shelfString = res.getResponse().getContentAsString();

        //convert the response into a shelf
        ObjectMapper mapper = new ObjectMapper();
        Shelf actual = mapper.readValue(shelfString, Shelf.class);

        assertEquals(oldShelf, actual);

    }

    @Test
    public void editShelfTestInvalidId() throws Exception {
        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);
        a.setId(1L);
        b.setId(2L);

        List<Book> books = new ArrayList<>();
        books.add(a);
        books.add(b);

        Shelf oldShelf = new Shelf(books, 5);
        oldShelf.setId(1L);
        when(shelfRepository.findById(1L)).thenReturn(Optional.of(oldShelf));


        //turn shelf into JSON string to pass to POST req
        String bookJson = "{\"id\":1,\"shelves\":[{\"id\":1,\"title\":\"Charlotte\'s Web\",\"author\":\"E.B. White\",\"price\":9.99}],\"size\":10}";

        MvcResult res = this.mockMvc.perform(put("/shelves/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();

    }


}