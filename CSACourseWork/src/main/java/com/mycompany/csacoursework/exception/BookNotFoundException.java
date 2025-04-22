/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 * Custom Exception for Book not Found
 *
 * @author Yasith
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * @param message
     */
    public BookNotFoundException(String message) {
        super(message);
    }

    /**
     * Generate a error message
     *
     * @param id
     */
    public BookNotFoundException(Long id) {
        super("Book with ID " + id + " does not exit.Please try with another valid ID");
    }

}
