/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 * Custom Exception for Customer not found
 * @author Yasith
 */
public class CustomerNotFoundException extends RuntimeException{
    
    /**
     * 
     * @param message 
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Generate an error message
     * @param id 
     */
    public CustomerNotFoundException(Long id) {
        super("Customer with ID " + id + " does not exist.");
    }

}
