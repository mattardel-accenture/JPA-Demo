package com.jpa.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "genre")
public class Genre {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "BOOKS_GENRES",
            joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    )
    private List<Book> books = new ArrayList<>();

    @Version
    private Long version;

    // Constructors
    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Genre() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.getGenres().add(this);
        books.add(book);
    }

    public void removeBook(Book book) {
        book.getGenres().remove(this);
        this.getBooks().remove(book);
    }

    public void removeAllBooks() {
        for (Book book : this.getBooks()) {
            this.removeBook(book);
        }
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Genre)){
            return false;
        } else {
            Genre that = (Genre)obj;
            return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, description);
    }
}
