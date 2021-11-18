package com.jpa.demo.controllerTests;

import com.jpa.demo.controller.GenreController;
import com.jpa.demo.entity.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.demo.controller.ShelfController;
import com.jpa.demo.entity.Genre;
import com.jpa.demo.entity.Shelf;
import com.jpa.demo.repository.GenreRepository;
import com.jpa.demo.repository.ShelfRepository;
import com.jpa.demo.service.GenreService;
import com.jpa.demo.service.ShelfService;
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
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GenreRepository genreRepository;

    @Autowired
    @Mock
    private GenreService genreService;

    @Autowired
    @InjectMocks
    private GenreController genreController;


    @BeforeEach
    private void controllerSetup() {
        //we do this in order to get the Mock service into the Mock controller when using MockMVC
        genreService.setGenreRepository(genreRepository);
        genreController.setGenreService(genreService);
    }


    @Test
    public void addGenreTest() throws Exception {

        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);
        a.setId(1L);
        b.setId(2L);

        Genre newGenre = new Genre("Childrens Books", "These books are for kids");
        newGenre.setId(1L);
        newGenre.addBook(a);
        newGenre.addBook(b);

        //turn genre into JSON string to pass to POST req
        String genreJson = "{\"id\":1,\"books\":[{\"id\":1,\"title\":\"Charlotte\'s Web\",\"author\":\"E.B. White\",\"price\":9.99},{\"id\":2,\"title\":\"Matilda\",\"author\":\"Roald Dahl\",\"price\":19.99}],\"name\":\"Childrens Books\",\"description\":\"These books are for kids\"}";

        MvcResult res = this.mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreJson)
                        .characterEncoding("utf-8"))
                .andReturn();

        Mockito.verify(genreRepository).save(newGenre);
    }


    @Test
    public void listGenreTest() throws Exception {
        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);

        Genre newGenre = new Genre("Genre1", "This is Genre1");
        newGenre.addBook(a);
        newGenre.addBook(b);
        Genre newGenreOther = new Genre("Genre2", "This is Genre2");
        newGenreOther.addBook(a);
        newGenreOther.addBook(b);
        List<Genre> genreListExpected = new ArrayList<>();

        genreListExpected.add(newGenre);
        genreListExpected.add(newGenreOther);

        when(genreRepository.findAll()).thenReturn(genreListExpected);

        MvcResult res = this.mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String genreString = res.getResponse().getContentAsString();

        //convert the response into books
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List <Genre>> ref = new TypeReference <List <Genre>>(){};
        List<Genre> shelfListActual = mapper.readValue(genreString, ref);

        assertEquals(genreListExpected, shelfListActual);
    }

    @Test
    public void listGenreByIdTestValid() throws Exception {

        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);

        a.setId(1L);
        b.setId(2L);

        Genre expected = new Genre("Childrens Books", "These books are for kids");
        expected.addBook(a);
        expected.addBook(b);
        expected.setId(1L);

        when(genreRepository.findById(1L)).thenReturn(Optional.of(expected));

        MvcResult res = this.mockMvc.perform(get("/genres/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String genreString = res.getResponse().getContentAsString();

        //convert the response into a shelf
        ObjectMapper mapper = new ObjectMapper();
        Genre actual = mapper.readValue(genreString, Genre.class);

        assertEquals(expected, actual);
    }

    @Test
    public void listGenreByIdTestInvalid() throws Exception {

        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);

        a.setId(1L);
        b.setId(2L);

        Genre expected = new Genre("Childrens Books", "These books are for kids");
        expected.addBook(a);
        expected.addBook(b);
        expected.setId(1L);

        when(genreRepository.findById(1L)).thenReturn(Optional.of(expected));

        MvcResult res = this.mockMvc.perform(get("/genres/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void deleteGenreTest() throws Exception {
        MvcResult res = this.mockMvc.perform(delete("/genres/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(genreRepository).deleteById(1L);
    }

    @Test
    public void editGenreTestValidId() throws Exception {
        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);
        a.setId(1L);
        b.setId(2L);

        Genre oldGenre = new Genre("Childrens Books", "These books are for kids");
        oldGenre.addBook(a);
        oldGenre.addBook(b);
        oldGenre.setId(1L);
        when(genreRepository.findById(1L)).thenReturn(Optional.of(oldGenre));

        //turn genre into JSON string to pass to POST req
        String genreJson = "{\"id\":1,\"books\":[{\"id\":1,\"title\":\"Charlotte\'s Web\",\"author\":\"E.B. White\",\"price\":9.99},{\"id\":2,\"title\":\"Matilda\",\"author\":\"Roald Dahl\",\"price\":19.99}],\"name\":\"Childrens Books\",\"description\":\"These books are for kids\"}";

        MvcResult res = this.mockMvc.perform(put("/genres/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();


        String genreString = res.getResponse().getContentAsString();

        //convert the response into a shelf
        ObjectMapper mapper = new ObjectMapper();
        Genre actual = mapper.readValue(genreString, Genre.class);

        assertEquals(oldGenre, actual);

    }

    @Test
    public void editGenreTestInvalidId() throws Exception {
        Book a = new Book("Charlotte's Web", "E.B. White", 9.99);
        Book b = new Book("Matilda", "Roald Dahl", 19.99);
        a.setId(1L);
        b.setId(2L);

        Genre oldGenre = new Genre("Childrens Books", "These books are for kids");
        oldGenre.addBook(a);
        oldGenre.addBook(b);
        oldGenre.setId(1L);
        when(genreRepository.findById(1L)).thenReturn(Optional.of(oldGenre));

        //turn genre into JSON string to pass to POST req
        String genreJson = "{\"id\":1,\"books\":[{\"id\":1,\"title\":\"Charlotte\'s Web\",\"author\":\"E.B. White\",\"price\":9.99},{\"id\":2,\"title\":\"Matilda\",\"author\":\"Roald Dahl\",\"price\":19.99}],\"name\":\"Childrens Books\",\"description\":\"These books are for kids\"}";

        MvcResult res = this.mockMvc.perform(put("/genres/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}