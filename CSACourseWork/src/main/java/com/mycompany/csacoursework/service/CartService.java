/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.service;

import com.mycompany.csacoursework.exception.CustomerNotFoundException;
import com.mycompany.csacoursework.exception.InvalidInputException;
import com.mycompany.csacoursework.exception.OutOfStockException;
import com.mycompany.csacoursework.model.Book;
import com.mycompany.csacoursework.model.Cart;
import com.mycompany.csacoursework.model.CartItem;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Yasith
 */
public class CartService {
    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    private static CartService instance;
    private Map<Long, Cart> carts = new HashMap<>(); // customerId -> Cart
    
    private BookService bookService = BookService.getInstance();
    private CustomerService customerService = CustomerService.getInstance();
    
    // Private constructor for singleton pattern
    private CartService() {}
    
    // Get singleton instance
    public static synchronized CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }
    
    // Get or create cart for a customer
    public Cart getCart(Long customerId) {
        LOGGER.info("Getting cart for customer with ID: " + customerId);
        
        // Validate customer exists
        if (!customerService.customerExists(customerId)) {
            LOGGER.warning("Customer not found with ID: " + customerId);
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get or create cart
        Cart cart = carts.get(customerId);
        if (cart == null) {
            LOGGER.info("Creating new cart for customer with ID: " + customerId);
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }
        
        return cart;
    }
    
    // Add item to cart
    public Cart addItemToCart(Long customerId, Long bookId, int quantity) {
        LOGGER.info("Adding book with ID " + bookId + " to cart for customer with ID: " + customerId);
        
        // Validate inputs
        if (quantity <= 0) {
            LOGGER.warning("Invalid quantity: " + quantity);
            throw new InvalidInputException("Quantity must be greater than zero");
        }
        
        // Validate book exists and has sufficient stock
        Book book = bookService.getBookById(bookId);
        
        if (book.getStock() < quantity) {
            LOGGER.warning("Insufficient stock for book with ID: " + bookId);
            throw new OutOfStockException(bookId, quantity, book.getStock());
        }
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Check if book already in cart
        CartItem existingItem = cart.findItem(bookId);
        
        if (existingItem != null) {
            // Update quantity
            LOGGER.info("Book already in cart, updating quantity from " + existingItem.getQuantity() + " to " + (existingItem.getQuantity() + quantity));
            
            // Check if new total quantity exceeds stock
            if (existingItem.getQuantity() + quantity > book.getStock()) {
                LOGGER.warning("Total quantity would exceed available stock");
                throw new OutOfStockException(bookId, existingItem.getQuantity() + quantity, book.getStock());
            }
            
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Add new item
            LOGGER.info("Adding new item to cart");
            CartItem newItem = new CartItem(bookId, book.getTitle(), quantity, book.getPrice());
            cart.addItem(newItem);
        }
        
        return cart;
    }
    
    // Update item quantity
    public Cart updateItemQuantity(Long customerId, Long bookId, int quantity) {
        LOGGER.info("Updating quantity for book with ID " + bookId + " in cart for customer with ID: " + customerId);
        
        // Validate inputs
        if (quantity <= 0) {
            LOGGER.warning("Invalid quantity: " + quantity);
            throw new InvalidInputException("Quantity must be greater than zero");
        }
        
        // Validate book exists and has sufficient stock
        Book book = bookService.getBookById(bookId);
        
        if (book.getStock() < quantity) {
            LOGGER.warning("Insufficient stock for book with ID: " + bookId);
            throw new OutOfStockException(bookId, quantity, book.getStock());
        }
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Check if book in cart
        CartItem item = cart.findItem(bookId);
        
        if (item == null) {
            LOGGER.warning("Book with ID " + bookId + " not found in cart");
            throw new InvalidInputException("Book with ID " + bookId + " not in cart");
        }
        
        // Update quantity
        LOGGER.info("Updating quantity from " + item.getQuantity() + " to " + quantity);
        item.setQuantity(quantity);
        
        return cart;
    }
    
    // Remove item from cart
    public Cart removeItemFromCart(Long customerId, Long bookId) {
        LOGGER.info("Removing book with ID " + bookId + " from cart for customer with ID: " + customerId);
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Check if book in cart
        CartItem item = cart.findItem(bookId);
        
        if (item == null) {
            LOGGER.warning("Book with ID " + bookId + " not found in cart");
            throw new InvalidInputException("Book with ID " + bookId + " not in cart");
        }
        
        // Remove item
        cart.removeItem(bookId);
        LOGGER.info("Item removed from cart");
        
        return cart;
    }
    
    // Clear cart
    public void clearCart(Long customerId) {
        LOGGER.info("Clearing cart for customer with ID: " + customerId);
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Clear items
        cart.getItems().clear();
        LOGGER.info("Cart cleared");
    }
    
}
