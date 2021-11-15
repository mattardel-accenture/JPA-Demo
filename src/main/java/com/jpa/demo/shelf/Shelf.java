package com.jpa.demo.shelf;

import com.jpa.demo.book.Book;

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
    private int size;

    protected Shelf() {}

    public Shelf(List<Book> shelves, int size){
        this.books = shelves;
        this.size = size;
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
    public int getSize(){
        return this.size;
    }

    public void setSize(int newSize){
        this.size = newSize;
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

            return Objects.equals(this.books, that.books) && Objects.equals(this.size, that.size);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(books, size);
    }

}