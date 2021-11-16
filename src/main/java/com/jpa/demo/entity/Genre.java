package com.jpa.demo.entity;
import com.jpa.demo.entity.Book;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {

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

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Genre(List<Book> books, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.books = books;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(name = "BOOKS_GENRES",
            joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    )
    private List<Book> books;
}
