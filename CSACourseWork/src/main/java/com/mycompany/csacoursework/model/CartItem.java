/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

/**
 * Public class for Cart
 *
 * @author Yasith
 */
public class CartItem {

    private Long bookId;
    private String title;
    private int quantity;
    private double price;

    /**
     * Default Constructor
     */
    public CartItem() {
    }

    /**
     * Constructor
     *
     * @param bookId
     * @param title
     * @param quantity
     * @param price
     */
    public CartItem(Long bookId, String title, int quantity, double price) {
        this.bookId = bookId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    /**
     * Getter for bookId
     *
     * @return
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * Setter for bookId
     *
     * @param bookId
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
     * Getter for quantity
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter for quantity
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
     * Method for calculate total price * quantity
     *
     * @return
     */
    public double getTotal() {
        return price * quantity;
    }
}
