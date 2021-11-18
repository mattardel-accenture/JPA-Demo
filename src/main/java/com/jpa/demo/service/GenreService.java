package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.Genre;
import com.jpa.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        Optional<Genre> foundGenre = getGenreById(id);
        if (!foundGenre.isEmpty()) {
            Genre retrievedGenre = foundGenre.get();
            retrievedGenre.removeAllBooks();
            genreRepository.deleteById(id);
        }
    }

    public Boolean updateGenre(Long id, Genre genre) {
        Optional<Genre> foundGenre = getGenreById(id);
        //need to assert that foundGenre isn't empty before we call get
        if (foundGenre.isEmpty()) {
            return Boolean.FALSE;
        }

        Genre updatedGenre = foundGenre.get();

        updatedGenre.setName(genre.getName());
        updatedGenre.setDescription(genre.getDescription());
        if (!genre.getBooks().isEmpty()) {
            updatedGenre.removeAllBooks();
            for (Book book : genre.getBooks()) {
                updatedGenre.addBook(book);
            }
        }

        saveGenre(updatedGenre);
        return Boolean.TRUE;
    }
}
