/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.service;

import com.mycompany.csacoursework.exception.CustomerNotFoundException;
import com.mycompany.csacoursework.exception.InvalidInputException;
import com.mycompany.csacoursework.model.Customer;
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
public class CustomerService {
    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
    private static CustomerService instance;
    private Map<Long, Customer> customers = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);
    
    // Private constructor for singleton pattern
    private CustomerService() {}
    
    // Get singleton instance
    public static synchronized CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }
    
    // Create customer
    public Customer createCustomer(Customer customer) {
        LOGGER.info("Creating a new customer: " + customer.getFirstName() + " " + customer.getLastName());
        
        // Validate customer data
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            LOGGER.warning("Attempt to create customer with empty first name");
            throw new InvalidInputException("Customer first name cannot be empty");
        }
        
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            LOGGER.warning("Attempt to create customer with empty last name");
            throw new InvalidInputException("Customer last name cannot be empty");
        }
        
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            LOGGER.warning("Attempt to create customer with empty email");
            throw new InvalidInputException("Customer email cannot be empty");
        }
        
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            LOGGER.warning("Attempt to create customer with empty password");
            throw new InvalidInputException("Customer password cannot be empty");
        }
        
        // Simple email validation
        if (!customer.getEmail().contains("@")) {
            LOGGER.warning("Attempt to create customer with invalid email format");
            throw new InvalidInputException("Invalid email format");
        }
        
        // Check if email already exists
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equals(customer.getEmail()));
        
        if (emailExists) {
            LOGGER.warning("Attempt to create customer with existing email: " + customer.getEmail());
            throw new InvalidInputException("Email already registered");
        }
        
        customer.setId(idCounter.getAndIncrement());
        customers.put(customer.getId(), customer);
        LOGGER.info("Customer created successfully with ID: " + customer.getId());
        return customer;
    }
    
    // Get all customers
    public List<Customer> getAllCustomers() {
        LOGGER.info("Retrieving all customers. Total count: " + customers.size());
        return new ArrayList<>(customers.values());
    }
    
    // Get customer by ID
    public Customer getCustomerById(Long id) {
        LOGGER.info("Retrieving customer with ID: " + id);
        Customer customer = customers.get(id);
        if (customer == null) {
            LOGGER.warning("Customer not found with ID: " + id);
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }
    
    // Update customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        LOGGER.info("Updating customer with ID: " + id);
        
        if (!customers.containsKey(id)) {
            LOGGER.warning("Attempt to update non-existent customer with ID: " + id);
            throw new CustomerNotFoundException(id);
        }
        
        // Validate updated data
        if (updatedCustomer.getFirstName() == null || updatedCustomer.getFirstName().trim().isEmpty()) {
            LOGGER.warning("Attempt to update customer with empty first name");
            throw new InvalidInputException("Customer first name cannot be empty");
        }
        
        if (updatedCustomer.getLastName() == null || updatedCustomer.getLastName().trim().isEmpty()) {
            LOGGER.warning("Attempt to update customer with empty last name");
            throw new InvalidInputException("Customer last name cannot be empty");
        }
        
        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty()) {
            LOGGER.warning("Attempt to update customer with empty email");
            throw new InvalidInputException("Customer email cannot be empty");
        }
        
        // Simple email validation
        if (!updatedCustomer.getEmail().contains("@")) {
            LOGGER.warning("Attempt to update customer with invalid email format");
            throw new InvalidInputException("Invalid email format");
        }
        
        // Check if updated email already exists with another customer
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equals(updatedCustomer.getEmail()) && !c.getId().equals(id));
        
        if (emailExists) {
            LOGGER.warning("Attempt to update customer with existing email: " + updatedCustomer.getEmail());
            throw new InvalidInputException("Email already registered with another customer");
        }
        
        // Preserve the password if not provided in the update
        if (updatedCustomer.getPassword() == null || updatedCustomer.getPassword().trim().isEmpty()) {
            updatedCustomer.setPassword(customers.get(id).getPassword());
        }
        
        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        LOGGER.info("Customer updated successfully with ID: " + id);
        return updatedCustomer;
    }
    
    // Delete customer
    public void deleteCustomer(Long id) {
        LOGGER.info("Deleting customer with ID: " + id);
        
        if (!customers.containsKey(id)) {
            LOGGER.warning("Attempt to delete non-existent customer with ID: " + id);
            throw new CustomerNotFoundException(id);
        }
        
        customers.remove(id);
        LOGGER.info("Customer deleted successfully with ID: " + id);
    }
    
    // Check if customer exists
    public boolean customerExists(Long id) {
        boolean exists = customers.containsKey(id);
        LOGGER.info("Checking if customer exists with ID " + id + ": " + exists);
        return exists;
    }
}
