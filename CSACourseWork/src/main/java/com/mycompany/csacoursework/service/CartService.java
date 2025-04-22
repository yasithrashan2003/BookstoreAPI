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
import java.util.logging.Level;
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
    private CartService() {
    }

    // Get singleton instance
    public static synchronized CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    /**
     * Get Cart
     *
     * @param customerId
     * @return
     */
    public Cart getCart(Long customerId) {
        LOGGER.log(Level.INFO, "Getting cart for customer with ID: {0}", customerId);

        // Validate customer exists
        if (!customerService.customerExists(customerId)) {
            LOGGER.log(Level.WARNING, "Customer not found with ID: {0}", customerId);
            throw new CustomerNotFoundException(customerId);
        }

        // Get and create cart
        Cart cart = carts.get(customerId);
        if (cart == null) {
            LOGGER.log(Level.INFO, "Creating new cart for customer with ID: {0}", customerId);
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }

        return cart;
    }

    /**
     * add item to cart
     *
     * @param customerId
     * @param bookId
     * @param quantity
     * @return
     */
    public Cart addItemToCart(Long customerId, Long bookId, int quantity) {
        LOGGER.log(Level.INFO, "Adding book with ID {0} to cart for customer with ID: {1}", new Object[]{bookId, customerId});

        // Validate inputs
        if (quantity <= 0) {
            LOGGER.log(Level.WARNING, "Invalid quantity: {0}", quantity);
            throw new InvalidInputException("Quantity must be greater than zero");
        }

        // Validate book exists and has sufficient stock
        Book book = bookService.getBookById(bookId);

        if (book.getStock() < quantity) {
            LOGGER.log(Level.WARNING, "Insufficient stock for book with ID: {0}", bookId);
            throw new OutOfStockException(bookId, quantity, book.getStock());
        }

        // Get cart
        Cart cart = getCart(customerId);

        // Check if book already in cart
        CartItem existingItem = cart.findItem(bookId);

        if (existingItem != null) {
            // Update quantity
            LOGGER.log(Level.INFO, "Book already in cart, updating quantity from {0} to {1}{2}", new Object[]{existingItem.getQuantity(), existingItem.getQuantity(), quantity});

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

    /**
     * update item quantity
     *
     * @param customerId
     * @param bookId
     * @param quantity
     * @return
     */
    public Cart updateItemQuantity(Long customerId, Long bookId, int quantity) {
        LOGGER.log(Level.INFO, "Updating quantity for book with ID {0} in cart for customer with ID: {1}", new Object[]{bookId, customerId});

        // Validate inputs
        if (quantity <= 0) {
            LOGGER.log(Level.WARNING, "Invalid quantity: {0}", quantity);
            throw new InvalidInputException("Quantity must be greater than zero");
        }

        // Validate book exists and has sufficient stock
        Book book = bookService.getBookById(bookId);

        if (book.getStock() < quantity) {
            LOGGER.log(Level.WARNING, "Insufficient stock for book with ID: {0}", bookId);
            throw new OutOfStockException(bookId, quantity, book.getStock());
        }

        // Get cart
        Cart cart = getCart(customerId);

        // Check if book in cart
        CartItem item = cart.findItem(bookId);

        if (item == null) {
            LOGGER.log(Level.WARNING, "Book with ID {0} not found in cart", bookId);
            throw new InvalidInputException("Book with ID " + bookId + " not in cart");
        }

        // Update quantity
        LOGGER.log(Level.INFO, "Updating quantity from {0} to {1}", new Object[]{item.getQuantity(), quantity});
        item.setQuantity(quantity);

        return cart;
    }

    /**
     * Remove item form cart
     *
     * @param customerId
     * @param bookId
     * @return
     */
    public Cart removeItemFromCart(Long customerId, Long bookId) {
        LOGGER.log(Level.INFO, "Removing book with ID {0} from cart for customer with ID: {1}", new Object[]{bookId, customerId});

        // Get cart
        Cart cart = getCart(customerId);

        // Check if book in cart
        CartItem item = cart.findItem(bookId);

        if (item == null) {
            LOGGER.log(Level.WARNING, "Book with ID {0} not found in cart", bookId);
            throw new InvalidInputException("Book with ID " + bookId + " not in cart");
        }

        // Remove item
        cart.removeItem(bookId);
        LOGGER.info("Item removed from cart");

        return cart;
    }

    /**
     * clear cart
     *
     * @param customerId
     */
    public void clearCart(Long customerId) {
        LOGGER.log(Level.INFO, "Clearing cart for customer with ID: {0}", customerId);

        // Get cart
        Cart cart = getCart(customerId);

        // Clear items
        cart.getItems().clear();
        LOGGER.info("Cart cleared");
    }

}
