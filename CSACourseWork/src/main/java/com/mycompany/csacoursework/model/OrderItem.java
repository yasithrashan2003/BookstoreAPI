/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

/**
 * Public class for OrderItem
 *
 * @author Yasith
 */
public class OrderItem {

    private Long bookId;
    private String title;
    private int quantity;
    private double price;

    /**
     * default constructor
     */
    public OrderItem() {
    }

    /**
     * Constructor
     *
     * @param bookId
     * @param title
     * @param quantity
     * @param price
     */
    public OrderItem(Long bookId, String title, int quantity, double price) {
        this.bookId = bookId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Constructor for cartItem
     *
     * @param cartItem
     */
    public OrderItem(CartItem cartItem) {
        this.bookId = cartItem.getBookId();
        this.title = cartItem.getTitle();
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getPrice();
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
     * Getter for Quantity
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
     * Setter for Price
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method for getTotal price * quantity
     *
     * @return
     */
    public double getTotal() {
        return price * quantity;
    }
}
