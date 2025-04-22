/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 * Custom exception for Cart not found
 *
 * @author Yasith
 */
public class CartNotFoundException extends RuntimeException {

    /**
     *
     * @param message
     */
    public CartNotFoundException(String message) {
        super(message);
    }

    /**
     * Generate an error message
     *
     * @param customerId
     */
    public CartNotFoundException(Long customerId) {
        super("Cart not found for customer with ID " + customerId);
    }
}
