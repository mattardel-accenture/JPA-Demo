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

    private GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") Long id){
        Optional<Genre> foundGenre = genreService.getGenreById(id);
        //need to assert that foundGenre isn't empty before we call get
        if(foundGenre.isEmpty()){
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
    public void deleteGenre(@PathVariable("id") Long id){
        genreService.deleteGenre(id);
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<Genre> editGenre(@PathVariable("id") Long id, @RequestBody Genre updateRequest) {

        Optional<Genre> foundGenre = genreService.getGenreById(id);
        //need to assert that foundGenre isn't empty before we call get
        if(foundGenre.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Genre updatedGenre = foundGenre.get();

        updatedGenre.setName(updateRequest.getName());
        updatedGenre.setDescription(updateRequest.getDescription());

        genreService.saveGenre(updatedGenre);

        return new ResponseEntity<Genre>(updatedGenre, HttpStatus.OK);

    }

    @Autowired
    public void setGenreService(GenreService genreService){
        this.genreService = genreService;
    }


}
