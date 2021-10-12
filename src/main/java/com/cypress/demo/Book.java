package com.cypress.demo;

import javax.persistence.*;
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

    protected Book() {}

    public Book(String title, String author, double price){
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle(){
        return this.title;
    }



    public String getAuthor(){
        return this.author;
    }
    public Double getPrice(){
        return this.price;
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