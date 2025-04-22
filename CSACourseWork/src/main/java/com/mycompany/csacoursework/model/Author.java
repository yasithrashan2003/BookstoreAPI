/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.model;

/**
 * Public class for Author
 *
 * @author Yasith
 */
public class Author {

    private Long id;
    private String firstName;
    private String lastName;
    private String biography;

    /**
     * Default Constructor
     */
    public Author() {
    }

    /**
     * Constructor
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param biography
     */
    public Author(Long id, String firstName, String lastName, String biography) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
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
     * Getter for firstName
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for biography
     *
     * @return
     */
    public String getBiography() {
        return biography;
    }

    /**
     * setter for biography
     *
     * @param biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Method for get full name
     *
     * @return
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
