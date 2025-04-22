/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 * Custom Exception for Out of stock
 *
 * @author Yasith
 */
public class OutOfStockException extends RuntimeException {

    /**
     *
     * @param message
     */
    public OutOfStockException(String message) {
        super(message);
    }

    /**
     * Generate an error message
     *
     * @param bookId
     * @param requested
     * @param available
     */
    public OutOfStockException(Long bookId, int requested, int available) {
        super("Book with ID " + bookId + " has insufficient stock. Requested: " + requested + ", Available: " + available);
    }
}
