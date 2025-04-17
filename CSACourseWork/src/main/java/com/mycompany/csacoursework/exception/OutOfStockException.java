/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 *
 * @author Yasith
 */
public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }
    
    public OutOfStockException(Long bookId) {
        super("Book with ID " + bookId + " is out of stock.");
    }
}
