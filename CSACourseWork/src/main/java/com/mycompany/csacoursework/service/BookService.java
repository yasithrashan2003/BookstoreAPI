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
        initializeDefaultBooks();
    }

    // Get singleton instance
    public static synchronized BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    /**
     * Add default Books
     */
    private void initializeDefaultBooks() {
        // First, make sure authors exist
        if (AuthorService.getInstance().authorExists(1L)
                && AuthorService.getInstance().authorExists(2L)
                && AuthorService.getInstance().authorExists(3L)) {

            Book book1 = new Book();
            book1.setTitle("Dune");
            book1.setAuthorId(1L);
            book1.setIsbn("978-0441172719");
            book1.setPublicationYear(1965);
            book1.setPrice(14.99);
            book1.setStock(75);
            book1.setId(idCounter.getAndIncrement());
            books.put(book1.getId(), book1);

            Book book2 = new Book();
            book2.setTitle("Harry Potter and the Philosopher's Stone");
            book2.setAuthorId(2L);
            book2.setIsbn("978-0747532743");
            book2.setPublicationYear(1997);
            book2.setPrice(12.99);
            book2.setStock(100);
            book2.setId(idCounter.getAndIncrement());
            books.put(book2.getId(), book2);

            Book book3 = new Book();
            book3.setTitle("1984");
            book3.setAuthorId(3L);
            book3.setIsbn("978-0451524935");
            book3.setPublicationYear(1949);
            book3.setPrice(9.99);
            book3.setStock(50);
            book3.setId(idCounter.getAndIncrement());
            books.put(book3.getId(), book3);

            LOGGER.info("Default books initialized");
        } else {
            LOGGER.warning("Could not initialize default books - authors not found");
        }
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

        // Validation for book price
        if (book.getPrice() <= 0) {
            LOGGER.log(Level.WARNING, "Attempt to create book with non-positive price: {0}", book.getPrice());
            throw new InvalidInputException("Book price must be greater than zero");
        }

        // Validation for book stock
        if (book.getStock() < 0) {
            LOGGER.log(Level.WARNING, "Attempt to create book with negative stock: {0}", book.getStock());
            throw new InvalidInputException("Book stock cannot be negative");
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

        // Validation for Book price
        if (updatedBook.getPrice() <= 0) {
            LOGGER.log(Level.WARNING, "Attempt to update book with non-positive price: {0}", updatedBook.getPrice());
            throw new InvalidInputException("Book price must be greater than zero");
        }

        // Validation for Book Stock
        if (updatedBook.getStock() < 0) {
            LOGGER.log(Level.WARNING, "Attempt to update book with negative stock: {0}", updatedBook.getStock());
            throw new InvalidInputException("Book stock cannot be negative");
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
