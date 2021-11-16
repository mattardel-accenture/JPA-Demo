package com.jpa.demo.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private double price;
    @ManyToOne
    private Shelf parent;
    @ManyToMany
    private List<Genre> genres;


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
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Book)){
            return false;
        } else {
            Book that = (Book)obj;

            return Objects.equals(this.title, that.title) && Objects.equals(this.author, that.author) && Objects.equals(this.price, that.price);
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(title, author, price);
    }

}