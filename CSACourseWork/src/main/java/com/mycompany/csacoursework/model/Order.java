/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yasith
 */
public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status; // e.g., "PLACED", "SHIPPED", "DELIVERED"
    private List<OrderItem> items;
    private double total;
    
    // Default constructor
    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }
    
    // Parameterized constructor
    public Order(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = "PLACED";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<OrderItem> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    // Helper methods
    public void addItem(OrderItem item) {
        this.items.add(item);
        this.total += item.getTotal();
    }
    
    public void calculateTotal() {
        this.total = this.items.stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();
    }
}
