/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Public class for Order
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

    /**
     * Default Constructor
     */
    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }

    /**
     * Constructor
     *
     * @param id
     * @param customerId
     */
    public Order(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = "PLACED";
    }

    // Getters and Setters
    /**
     * Getter for id
     *
     * @return
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
     * Getter for customerId
     *
     * @return
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Setter for customerId
     *
     * @param customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for OrderDate
     *
     * @return
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Setter for orderDate
     *
     * @param orderDate
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Getter for status
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for status
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter for items
     *
     * @return
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Setter for items
     *
     * @param items
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Getter for total
     *
     * @return
     */
    public double getTotal() {
        return total;
    }

    /**
     * Setter for total
     *
     * @param total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Method for addItem
     *
     * @param item
     */
    public void addItem(OrderItem item) {
        this.items.add(item);
        this.total += item.getTotal();
    }

    /**
     * method for calculateTotal
     */
    public void calculateTotal() {
        this.total = this.items.stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();
    }
}
