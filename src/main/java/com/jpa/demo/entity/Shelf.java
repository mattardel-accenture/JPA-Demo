package com.jpa.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedEntityGraph(name = "shelf-book-graph",
        attributeNodes = @NamedAttributeNode("books")
)
@Table(name = "shelf")
public class Shelf {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(
            mappedBy = "shelf",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();
    private String location;
    private String room;
    @Version
    private Long version;

    // Constructors
    public Shelf() {}

    public Shelf (String location, String room) {
        this.location = location;
        this.room = room;
    }

    // Getters and Setters
    public List<Book> getBooks() {
        return this.books;
    }
    public void addBook(Book newBook){
        books.add(newBook);
        newBook.setShelf(this);
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long newId) {
        id = newId;
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
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