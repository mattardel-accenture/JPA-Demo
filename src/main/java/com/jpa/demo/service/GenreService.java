package com.jpa.demo.service;

import com.jpa.demo.entity.Genre;
import com.jpa.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    public List<Genre> getGenresService() {
        return genreRepository.findAll();
    }
    public Optional<Genre> getGenreByIdService(Long id){
        return(genreRepository.findById(id));
    }

    public void saveGenreService(Genre genre){
        genreRepository.save(genre);
    }

    public void deleteGenreService(Long id){
        genreRepository.deleteById(id);
    }

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }



}
