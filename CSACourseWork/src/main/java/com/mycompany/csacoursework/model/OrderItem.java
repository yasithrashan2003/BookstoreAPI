/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

/**
 *
 * @author Yasith
 */
public class OrderItem {
    private Long bookId;
    private String title;
    private int quantity;
    private double price;
    
    // Default constructor
    public OrderItem() {
    }
    
    // Parameterized constructor
    public OrderItem(Long bookId, String title, int quantity, double price) {
        this.bookId = bookId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Constructor from CartItem
    public OrderItem(CartItem cartItem) {
        this.bookId = cartItem.getBookId();
        this.title = cartItem.getTitle();
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getPrice();
    }
    
    // Getters and Setters
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    // Helper method
    public double getTotal() {
        return price * quantity;
    }
}
