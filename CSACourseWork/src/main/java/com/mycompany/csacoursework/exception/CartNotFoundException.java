/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 *
 * @author Yasith
 */
public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public CartNotFoundException(Long customerId) {
        super("Cart not found for customer with ID " + customerId);
    }
}
