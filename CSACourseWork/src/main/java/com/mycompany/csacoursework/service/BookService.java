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
    private BookService() {}
    
    // Get singleton instance
    public static synchronized BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }
    
    // Create a book
    public Book createBook(Book book) {
        LOGGER.info("Creating a new book: " + book.getTitle());
        
        // Validate book data
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            LOGGER.warning("Attempt to create book with empty title");
            throw new InvalidInputException("Book title cannot be empty");
        }
        
        if (book.getPublicationYear() > java.time.Year.now().getValue()) {
            LOGGER.warning("Attempt to create book with future publication year: " + book.getPublicationYear());
            throw new InvalidInputException("Publication year cannot be in the future");
        }
        
        // Set a new ID and save the book
        book.setId(idCounter.getAndIncrement());
        books.put(book.getId(), book);
        LOGGER.info("Book created successfully with ID: " + book.getId());
        return book;
    }
    
    // Get all books
    public List<Book> getAllBooks() {
        LOGGER.info("Retrieving all books. Total count: " + books.size());
        return new ArrayList<>(books.values());
    }
    
    // Get book by ID
    public Book getBookById(Long id) {
        LOGGER.info("Retrieving book with ID: " + id);
        Book book = books.get(id);
        if (book == null) {
            LOGGER.warning("Book not found with ID: " + id);
            throw new BookNotFoundException(id);
        }
        return book;
    }
    
    // Update book
    public Book updateBook(Long id, Book updatedBook) {
        LOGGER.info("Updating book with ID: " + id);
        
        if (!books.containsKey(id)) {
            LOGGER.warning("Attempt to update non-existent book with ID: " + id);
            throw new BookNotFoundException(id);
        }
        
        // Validate updated data
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            LOGGER.warning("Attempt to update book with empty title");
            throw new InvalidInputException("Book title cannot be empty");
        }
        
        if (updatedBook.getPublicationYear() > java.time.Year.now().getValue()) {
            LOGGER.warning("Attempt to update book with future publication year: " + updatedBook.getPublicationYear());
            throw new InvalidInputException("Publication year cannot be in the future");
        }
        
        updatedBook.setId(id);
        books.put(id, updatedBook);
        LOGGER.info("Book updated successfully with ID: " + id);
        return updatedBook;
    }
    
    // Delete book
    public void deleteBook(Long id) {
        LOGGER.info("Deleting book with ID: " + id);
        
        if (!books.containsKey(id)) {
            LOGGER.warning("Attempt to delete non-existent book with ID: " + id);
            throw new BookNotFoundException(id);
        }
        
        books.remove(id);
        LOGGER.info("Book deleted successfully with ID: " + id);
    }
    
    // Get books by author ID
    public List<Book> getBooksByAuthor(Long authorId) {
        LOGGER.info("Retrieving books for author with ID: " + authorId);
        
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorId() != null && book.getAuthorId().equals(authorId)) {
                authorBooks.add(book);
            }
        }
        
        LOGGER.info("Found " + authorBooks.size() + " books for author with ID: " + authorId);
        return authorBooks;
    }
}