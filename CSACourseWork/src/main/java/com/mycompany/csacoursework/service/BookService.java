/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.service;

import com.mycompany.csacoursework.exception.BookNotFoundException;
import com.mycompany.csacoursework.exception.InvalidInputException;
import com.mycompany.csacoursework.model.Book;
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
public class BookService {

    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    private static BookService instance;
    private Map<Long, Book> books = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    // Private constructor for singleton pattern
    private BookService() {
    }

    // Get singleton instance
    public static synchronized BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    /**
     * Create a book
     *
     * @param book
     * @return
     */
    public Book createBook(Book book) {
        LOGGER.log(Level.INFO, "Creating a new book: {0}", book.getTitle());

        // Validate book data
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            LOGGER.warning("Attempt to create book with empty title");
            throw new InvalidInputException("Book title cannot be empty");
        }

        if (book.getPublicationYear() > java.time.Year.now().getValue()) {
            LOGGER.log(Level.WARNING, "Attempt to create book with future publication year: {0}", book.getPublicationYear());
            throw new InvalidInputException("Publication year cannot be in the future");
        }

        // Set a new ID and save the book
        book.setId(idCounter.getAndIncrement());
        books.put(book.getId(), book);
        LOGGER.log(Level.INFO, "Book created successfully with ID: {0}", book.getId());
        return book;
    }

    /**
     * Get all the Books
     *
     * @return
     */
    public List<Book> getAllBooks() {
        LOGGER.log(Level.INFO, "Retrieving all books. Total count: {0}", books.size());
        return new ArrayList<>(books.values());
    }

    /**
     * get book by id
     *
     * @param id
     * @return
     */
    public Book getBookById(Long id) {
        LOGGER.log(Level.INFO, "Retrieving book with ID: {0}", id);
        Book book = books.get(id);
        if (book == null) {
            LOGGER.log(Level.WARNING, "Book not found with ID: {0}", id);
            throw new BookNotFoundException(id);
        }
        return book;
    }

    /**
     * update book by id
     *
     * @param id
     * @param updatedBook
     * @return
     */
    public Book updateBook(Long id, Book updatedBook) {
        LOGGER.log(Level.INFO, "Updating book with ID: {0}", id);

        // validations
        if (!books.containsKey(id)) {
            LOGGER.log(Level.WARNING, "Attempt to update non-existent book with ID: {0}", id);
            throw new BookNotFoundException(id);
        }

        // Validate updated data
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            LOGGER.warning("Attempt to update book with empty title");
            throw new InvalidInputException("Book title cannot be empty");
        }

        if (updatedBook.getPublicationYear() > java.time.Year.now().getValue()) {
            LOGGER.log(Level.WARNING, "Attempt to update book with future publication year: {0}", updatedBook.getPublicationYear());
            throw new InvalidInputException("Publication year cannot be in the future");
        }

        updatedBook.setId(id);
        books.put(id, updatedBook);
        LOGGER.log(Level.INFO, "Book updated successfully with ID: {0}", id);
        return updatedBook;
    }

    /**
     * delete book
     *
     * @param id
     */
    public void deleteBook(Long id) {
        LOGGER.log(Level.INFO, "Deleting book with ID: {0}", id);

        if (!books.containsKey(id)) {
            LOGGER.log(Level.WARNING, "Attempt to delete non-existent book with ID: {0}", id);
            throw new BookNotFoundException(id);
        }

        books.remove(id);
        LOGGER.log(Level.INFO, "Book deleted successfully with ID: {0}", id);
    }

    /**
     * get book by author
     *
     * @param authorId
     * @return
     */
    public List<Book> getBooksByAuthor(Long authorId) {
        LOGGER.log(Level.INFO, "Retrieving books for author with ID: {0}", authorId);

        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorId() != null && book.getAuthorId().equals(authorId)) {
                authorBooks.add(book);
            }
        }

        LOGGER.log(Level.INFO, "Found {0} books for author with ID: {1}", new Object[]{authorBooks.size(), authorId});
        return authorBooks;
    }
}
