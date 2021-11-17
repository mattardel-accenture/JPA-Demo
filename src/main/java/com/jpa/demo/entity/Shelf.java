package com.jpa.demo.entity;

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
    private List<Book> books = new ArrayList<>();
    private String location;
    private String room;
    @Version
    private Long version;

    protected Shelf() {}

    public Shelf (List<Book> shelves, String location) {
        this.books = shelves;
        this.location = location;
    }

    public List<Book> getBooks(){
        return this.books;
    }
//    public void setBooks(List<Book> books){
//        this.books = books;
//    }
    public void addBook(Book newBook){
        books.add(newBook);
        newBook.setShelf(this);
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

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

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