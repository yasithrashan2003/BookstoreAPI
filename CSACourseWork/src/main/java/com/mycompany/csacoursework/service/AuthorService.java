/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.service;

import com.mycompany.csacoursework.exception.AuthorNotFoundException;
import com.mycompany.csacoursework.exception.InvalidInputException;
import com.mycompany.csacoursework.model.Author;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 *
 * @author Yasith
 */
public class AuthorService {
    private static final Logger LOGGER = Logger.getLogger(AuthorService.class.getName());
    private static AuthorService instance;
    private Map<Long, Author> authors = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);
    
    // Private constructor for singleton pattern
    private AuthorService() {}
    
    // Get singleton instance
    public static synchronized AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }
    
    // Create author
    public Author createAuthor(Author author) {
        LOGGER.info("Creating a new author: " + author.getFirstName() + " " + author.getLastName());
        
        if (author.getFirstName() == null || author.getFirstName().trim().isEmpty()) {
            LOGGER.warning("Attempt to create author with empty first name");
            throw new InvalidInputException("Author first name cannot be empty");
        }
        
        if (author.getLastName() == null || author.getLastName().trim().isEmpty()) {
            LOGGER.warning("Attempt to create author with empty last name");
            throw new InvalidInputException("Author last name cannot be empty");
        }
        
        author.setId(idCounter.getAndIncrement());
        authors.put(author.getId(), author);
        LOGGER.info("Author created successfully with ID: " + author.getId());
        return author;
    }
    
    // Get all authors
    public List<Author> getAllAuthors() {
        LOGGER.info("Retrieving all authors. Total count: " + authors.size());
        return new ArrayList<>(authors.values());
    }
    
    // Get author by ID
    public Author getAuthorById(Long id) {
        LOGGER.info("Retrieving author with ID: " + id);
        Author author = authors.get(id);
        if (author == null) {
            LOGGER.warning("Author not found with ID: " + id);
            throw new AuthorNotFoundException(id);
        }
        return author;
    }
    
    // Update author
    public Author updateAuthor(Long id, Author updatedAuthor) {
        LOGGER.info("Updating author with ID: " + id);
        
        if (!authors.containsKey(id)) {
            LOGGER.warning("Attempt to update non-existent author with ID: " + id);
            throw new AuthorNotFoundException(id);
        }
        
        if (updatedAuthor.getFirstName() == null || updatedAuthor.getFirstName().trim().isEmpty()) {
            LOGGER.warning("Attempt to update author with empty first name");
            throw new InvalidInputException("Author first name cannot be empty");
        }
        
        if (updatedAuthor.getLastName() == null || updatedAuthor.getLastName().trim().isEmpty()) {
            LOGGER.warning("Attempt to update author with empty last name");
            throw new InvalidInputException("Author last name cannot be empty");
        }
        
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        LOGGER.info("Author updated successfully with ID: " + id);
        return updatedAuthor;
    }
    
    // Delete author
    public void deleteAuthor(Long id) {
        LOGGER.info("Deleting author with ID: " + id);
        
        if (!authors.containsKey(id)) {
            LOGGER.warning("Attempt to delete non-existent author with ID: " + id);
            throw new AuthorNotFoundException(id);
        }
        
        authors.remove(id);
        LOGGER.info("Author deleted successfully with ID: " + id);
    }
    
    // Check if author exists
    public boolean authorExists(Long id) {
        boolean exists = authors.containsKey(id);
        LOGGER.info("Checking if author exists with ID " + id + ": " + exists);
        return exists;
    }
}