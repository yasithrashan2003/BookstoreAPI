/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

/**
 * Public class for Book
 *
 * @author Yasith
 */
public class Book {

    private Long id;
    private String title;
    private Long authorId;
    private String isbn;
    private int publicationYear;
    private double price;
    private int stock;

    /**
     * Default Constructor
     */
    public Book() {
    }

    /**
     * Constructor
     *
     * @param id
     * @param title
     * @param authorId
     * @param isbn
     * @param publicationYear
     * @param price
     * @param stock
     */
    public Book(Long id, String title, Long authorId, String isbn, int publicationYear, double price, int stock) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    /**
     * Getter for id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for title
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for authorId
     *
     * @return
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * Setter for authorId
     *
     * @param authorId
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * Getter for ISBN
     *
     * @return
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Setter for ISBN
     *
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Getter for publicationYear
     *
     * @return
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Setter for publicationYear
     *
     * @param publicationYear
     */
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Getter for price
     *
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for price
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for stock
     *
     * @return
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for Stock
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

}
