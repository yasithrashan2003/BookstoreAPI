/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 * Custom Exception for Author not found Extends RuntimeException
 *
 * @author Yasith
 */
public class AuthorNotFoundException extends RuntimeException {

    /**
     *
     * @param message include with exception
     */
    public AuthorNotFoundException(String message) {
        super(message);
    }

    /**
     * Generate a error message
     *
     * @param id
     */
    public AuthorNotFoundException(Long id) {
        super("Author with ID " + id + " does not exist.");
    }

}
