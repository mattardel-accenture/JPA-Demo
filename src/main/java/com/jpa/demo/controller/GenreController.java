package com.jpa.demo.controller;

import com.jpa.demo.entity.Genre;
import com.jpa.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") Long id) {
        Optional<Genre> foundGenre = genreService.getGenreById(id);
        if (foundGenre.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Genre updatedGenre = foundGenre.get();
        return new ResponseEntity<Genre>(updatedGenre, HttpStatus.OK);
    }

    @PostMapping("/genres")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        genreService.saveGenre(genre);
        return new ResponseEntity<Genre>(genre, HttpStatus.CREATED);
    }

    @DeleteMapping("/genres/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteGenre(id);
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<Genre> editGenre(@PathVariable("id") Long id, @RequestBody Genre genre) {
        Boolean successfulUpdate = genreService.updateGenre(id, genre);
        HttpStatus status = successfulUpdate ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Genre>(genre, status);
    }
}
