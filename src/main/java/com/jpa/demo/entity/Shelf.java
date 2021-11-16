package com.jpa.demo.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shelf")
public class Shelf{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<Book> books;
    private String location;

    protected Shelf() {}

    public Shelf (List<Book> shelves, String location) {
        this.books = shelves;
        this.location = location;
    }

    public List<Book> getBooks(){
        return this.books;
    }
    public void setBooks(List<Book> books){
        this.books = books;
    }
    public void addBook(Book newShelf){
        this.books.add(newShelf);
    }
    public String getLocation(){
        return this.location;
    }

    public void setLocation(String newLocation){
        this.location = newLocation;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long newId) {
        id = newId;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Shelf)){
            return false;
        } else {
            Shelf that = (Shelf)obj;

            return Objects.equals(this.books, that.books) && Objects.equals(this.location, that.location);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(books, location);
    }

}