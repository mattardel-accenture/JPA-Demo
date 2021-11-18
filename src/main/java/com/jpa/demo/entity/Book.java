package com.jpa.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private double price;
    private Boolean isDeleted = false;
    @Version
    private Long version;

    @ManyToOne()
    @JsonBackReference
    private Shelf shelf;

    @ManyToMany
    private List<Genre> genres = new ArrayList<>();

    protected Book() {}

    public Book(String title, String author, double price){
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String newAuthor){
        this.author = newAuthor;
    }

    public Double getPrice(){
        return this.price;
    }

    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long newId) {
        id = newId;
    }

    public Shelf getShelf() {
        return this.shelf;
    }
    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
    public String getShelfLocation() {
        return this.shelf != null ? this.shelf.getLocation() : null;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean v) {
        isDeleted = v;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        genre.addBook(this);
        getGenres().add(genre);
    }

    public void removeGenre(Genre genre) {
        genre.removeBook(this);
        getGenres().remove(genre);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Book)){
            return false;
        } else {
            Book that = (Book)obj;

            return Objects.equals(this.title, that.title)
                    && Objects.equals(this.author, that.author)
                    && Objects.equals(this.price, that.price);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

}