/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yasith
 */
public class Cart {
    private Long customerId;
    private List<CartItem> items;
    
    // Default constructor
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    // Parameterized constructor
    public Cart(Long customerId) {
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }
    
    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
    // Helper methods
    public void addItem(CartItem item) {
        this.items.add(item);
    }
    
    public void removeItem(Long bookId) {
        this.items.removeIf(item -> item.getBookId().equals(bookId));
    }
    
    public CartItem findItem(Long bookId) {
        return this.items.stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst()
                .orElse(null);
    }
    
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
    
    public int getItemCount() {
        return this.items.size();
    }
    
    public double getTotal() {
        return this.items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
