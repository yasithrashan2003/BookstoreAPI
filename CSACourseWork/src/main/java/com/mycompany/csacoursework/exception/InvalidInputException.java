/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

/**
 * Custom Exception for Invalid inputs
 *
 * @author Yasith
 */
public class InvalidInputException extends RuntimeException {

    /**
     *
     * @param message
     */
    public InvalidInputException(String message) {
        super(message);
    }

}
