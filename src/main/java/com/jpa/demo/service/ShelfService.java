package com.jpa.demo.service;

import com.jpa.demo.entity.Shelf;
import com.jpa.demo.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;

    public List<Shelf> getShelvesService() {
        return shelfRepository.findAll();
    }
    public Optional<Shelf> getShelfByIdService(Long id){
        return(shelfRepository.findById(id));
    }

    public void saveShelfService(Shelf shelf){
        shelfRepository.save(shelf);
    }

    public void deleteShelfService(Long id){
        shelfRepository.deleteById(id);
    }

}
