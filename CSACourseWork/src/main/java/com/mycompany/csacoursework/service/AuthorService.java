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
import java.util.logging.Level;
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

    // Private constructor for singleton design pattern
    private AuthorService() {
        initializeDefaultAuthors();
    }

    // Get singleton instance
    public static synchronized AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    /**
     * Default Authors
     */
    private void initializeDefaultAuthors() {

        Author author1 = new Author();
        author1.setFirstName("Frank");
        author1.setLastName("Herbert");
        author1.setBiography("American science fiction author best known for the novel Dune.");
        author1.setId(idCounter.getAndIncrement());
        authors.put(author1.getId(), author1);

        Author author2 = new Author();
        author2.setFirstName("J.K.");
        author2.setLastName("Rowling");
        author2.setBiography("British author best known for writing the Harry Potter fantasy series.");
        author2.setId(idCounter.getAndIncrement());
        authors.put(author2.getId(), author2);

        Author author3 = new Author();
        author3.setFirstName("George");
        author3.setLastName("Orwell");
        author3.setBiography("English novelist, essayist, journalist, and critic known for works like 1984 and Animal Farm.");
        author3.setId(idCounter.getAndIncrement());
        authors.put(author3.getId(), author3);

        LOGGER.info("Default authors initialized");
    }

    /**
     * create author
     *
     * @param author
     * @return
     */
    public Author createAuthor(Author author) {
        LOGGER.log(Level.INFO, "Creating a new author: {0} {1}", new Object[]{author.getFirstName(), author.getLastName()});

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
        LOGGER.log(Level.INFO, "Author created successfully with ID: {0}", author.getId());
        return author;
    }

    /**
     * get all authors
     *
     * @return
     */
    public List<Author> getAllAuthors() {
        LOGGER.log(Level.INFO, "Retrieving all authors. Total count: {0}", authors.size());
        return new ArrayList<>(authors.values());
    }

    /**
     * get author by id
     *
     * @param id
     * @return
     */
    public Author getAuthorById(Long id) {
        LOGGER.log(Level.INFO, "Retrieving author with ID: {0}", id);
        Author author = authors.get(id);
        if (author == null) {
            LOGGER.log(Level.WARNING, "Author not found with ID: {0}", id);
            throw new AuthorNotFoundException(id);
        }
        return author;
    }

    /**
     * update author
     *
     * @param id
     * @param updatedAuthor
     * @return
     */
    public Author updateAuthor(Long id, Author updatedAuthor) {
        LOGGER.log(Level.INFO, "Updating author with ID: {0}", id);

        // Validations
        if (!authors.containsKey(id)) {
            LOGGER.log(Level.WARNING, "Attempt to update non-existent author with ID: {0}", id);
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
        LOGGER.log(Level.INFO, "Author updated successfully with ID: {0}", id);
        return updatedAuthor;
    }

    /**
     * update author
     *
     * @param id
     */
    public void deleteAuthor(Long id) {
        LOGGER.log(Level.INFO, "Deleting author with ID: {0}", id);

        if (!authors.containsKey(id)) {
            LOGGER.log(Level.WARNING, "Attempt to delete non-existent author with ID: {0}", id);
            throw new AuthorNotFoundException(id);
        }

        authors.remove(id);
        LOGGER.log(Level.INFO, "Author deleted successfully with ID: {0}", id);
    }

    /**
     * check author exist or not
     *
     * @param id
     * @return
     */
    public boolean authorExists(Long id) {
        boolean exists = authors.containsKey(id);
        LOGGER.log(Level.INFO, "Checking if author exists with ID {0}: {1}", new Object[]{id, exists});
        return exists;
    }
}
