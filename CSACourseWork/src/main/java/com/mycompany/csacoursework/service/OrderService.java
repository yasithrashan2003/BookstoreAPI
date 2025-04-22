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
import com.mycompany.csacoursework.model.Order;
import com.mycompany.csacoursework.model.OrderItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * public class or order service
 *
 * @author Yasith
 */
public class OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());
    private static OrderService instance;
    private Map<Long, List<Order>> customerOrders = new HashMap<>(); // customerId -> list of orders
    private Map<Long, Order> orders = new HashMap<>(); // orderId -> order
    private AtomicLong orderIdCounter = new AtomicLong(1);

    private BookService bookService = BookService.getInstance();
    private CustomerService customerService = CustomerService.getInstance();
    private CartService cartService = CartService.getInstance();

    // Private constructor for singleton design pattern
    private OrderService() {
    }

    // Get singleton instance
    public static synchronized OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    /**
     * create order
     *
     * @param customerId
     * @return
     */
    public Order createOrder(Long customerId) {
        LOGGER.log(Level.INFO, "Creating order for customer with ID: {0}", customerId);

        // Validate customer exists
        if (!customerService.customerExists(customerId)) {
            LOGGER.log(Level.WARNING, "Customer not found with ID: {0}", customerId);
            throw new CustomerNotFoundException(customerId);
        }

        // Get customer's cart
        Cart cart = cartService.getCart(customerId);

        // Check if cart is empty
        if (cart.isEmpty()) {
            LOGGER.warning("Cannot create order with empty cart");
            throw new InvalidInputException("Cannot create order with empty cart");
        }

        // Create new order
        Order order = new Order(orderIdCounter.getAndIncrement(), customerId);

        // Add items from cart to order
        for (CartItem cartItem : cart.getItems()) {
            // Validate book exists and has sufficient stock
            Book book = bookService.getBookById(cartItem.getBookId());

            if (book.getStock() < cartItem.getQuantity()) {
                LOGGER.warning("Insufficient stock for book with ID: " + cartItem.getBookId());
                throw new OutOfStockException(cartItem.getBookId(), cartItem.getQuantity(), book.getStock());
            }

            // Update book stock
            book.setStock(book.getStock() - cartItem.getQuantity());
            bookService.updateBook(book.getId(), book);

            // Add item to order
            OrderItem orderItem = new OrderItem(cartItem);
            order.addItem(orderItem);
        }

        // Calculate total
        order.calculateTotal();

        // Save order
        orders.put(order.getId(), order);

        // Add order to customer's order list
        customerOrders.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order);

        // Clear cart after order creation
        cartService.clearCart(customerId);

        LOGGER.log(Level.INFO, "Order created successfully with ID: {0}", order.getId());
        return order;
    }

    /**
     * get customers orders
     *
     * @param customerId
     * @return
     */
    public List<Order> getCustomerOrders(Long customerId) {
        LOGGER.log(Level.INFO, "Getting orders for customer with ID: {0}", customerId);

        // Validate customer exists or not
        if (!customerService.customerExists(customerId)) {
            LOGGER.log(Level.WARNING, "Customer not found with ID: {0}", customerId);
            throw new CustomerNotFoundException(customerId);
        }

        return customerOrders.getOrDefault(customerId, new ArrayList<>());
    }

    /**
     * get order by customer
     *
     * @param customerId
     * @param orderId
     * @return
     */
    public Order getOrder(Long customerId, Long orderId) {
        LOGGER.log(Level.INFO, "Getting order with ID: {0} for customer with ID: {1}", new Object[]{orderId, customerId});

        // Validate customer exists
        if (!customerService.customerExists(customerId)) {
            LOGGER.log(Level.WARNING, "Customer not found with ID: {0}", customerId);
            throw new CustomerNotFoundException(customerId);
        }

        // Get order
        Order order = orders.get(orderId);

        // Check if order exists and belongs to customer
        if (order == null || !order.getCustomerId().equals(customerId)) {
            LOGGER.log(Level.WARNING, "Order not found with ID: {0} for customer with ID: {1}", new Object[]{orderId, customerId});
            throw new InvalidInputException("Order not found with ID: " + orderId + " for customer with ID: " + customerId);
        }

        return order;
    }
}
