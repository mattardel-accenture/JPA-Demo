package com.cypress.demo;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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

}
