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
import java.util.logging.Level;
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
    private CustomerService() {
        initializeDefaultCustomers();
    }

    // Get singleton instance
    public static synchronized CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    /**
     * Add default Customers
     */
    private void initializeDefaultCustomers() {
        // Create and add default customers
        Customer customer1 = new Customer();
        customer1.setFirstName("Jane");
        customer1.setLastName("Smith");
        customer1.setEmail("jane.smith@example.com");
        customer1.setPassword("securePassword123");
        customer1.setId(idCounter.getAndIncrement());
        customers.put(customer1.getId(), customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("John");
        customer2.setLastName("Doe");
        customer2.setEmail("john.doe@example.com");
        customer2.setPassword("password456");
        customer2.setId(idCounter.getAndIncrement());
        customers.put(customer2.getId(), customer2);

        LOGGER.info("Default customers initialized");
    }

    /**
     * create a customer
     *
     * @param customer
     * @return
     */
    public Customer createCustomer(Customer customer) {
        LOGGER.log(Level.INFO, "Creating a new customer: {0} {1}", new Object[]{customer.getFirstName(), customer.getLastName()});

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
            LOGGER.log(Level.WARNING, "Attempt to create customer with existing email: {0}", customer.getEmail());
            throw new InvalidInputException("Email already registered");
        }

        customer.setId(idCounter.getAndIncrement());
        customers.put(customer.getId(), customer);
        LOGGER.log(Level.INFO, "Customer created successfully with ID: {0}", customer.getId());
        return customer;
    }

    /**
     * get all the customers
     *
     * @return
     */
    public List<Customer> getAllCustomers() {
        LOGGER.log(Level.INFO, "Retrieving all customers. Total count: {0}", customers.size());
        return new ArrayList<>(customers.values());
    }

    /**
     * get customer by id
     *
     * @param id
     * @return
     */
    public Customer getCustomerById(Long id) {
        LOGGER.log(Level.INFO, "Retrieving customer with ID: {0}", id);
        Customer customer = customers.get(id);
        if (customer == null) {
            LOGGER.log(Level.WARNING, "Customer not found with ID: {0}", id);
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }

    /**
     * update customer
     *
     * @param id
     * @param updatedCustomer
     * @return
     */
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        LOGGER.log(Level.INFO, "Updating customer with ID: {0}", id);

        if (!customers.containsKey(id)) {
            LOGGER.log(Level.WARNING, "Attempt to update non-existent customer with ID: {0}", id);
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

        //  email validation
        if (!updatedCustomer.getEmail().contains("@")) {
            LOGGER.warning("Attempt to update customer with invalid email format");
            throw new InvalidInputException("Invalid email format");
        }

        // Check updated email already exists or not
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equals(updatedCustomer.getEmail()) && !c.getId().equals(id));

        if (emailExists) {
            LOGGER.log(Level.WARNING, "Attempt to update customer with existing email: {0}", updatedCustomer.getEmail());
            throw new InvalidInputException("Email already registered with another customer");
        }

        // Preserve the password
        if (updatedCustomer.getPassword() == null || updatedCustomer.getPassword().trim().isEmpty()) {
            updatedCustomer.setPassword(customers.get(id).getPassword());
        }

        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        LOGGER.log(Level.INFO, "Customer updated successfully with ID: {0}", id);
        return updatedCustomer;
    }

    /**
     * delete customer
     *
     * @param id
     */
    public void deleteCustomer(Long id) {
        LOGGER.log(Level.INFO, "Deleting customer with ID: {0}", id);

        if (!customers.containsKey(id)) {
            LOGGER.log(Level.WARNING, "Attempt to delete non-existent customer with ID: {0}", id);
            throw new CustomerNotFoundException(id);
        }

        customers.remove(id);
        LOGGER.log(Level.INFO, "Customer deleted successfully with ID: {0}", id);
    }

    /**
     * check customer is already exist or not
     *
     * @param id
     * @return
     */
    public boolean customerExists(Long id) {
        boolean exists = customers.containsKey(id);
        LOGGER.log(Level.INFO, "Checking if customer exists with ID {0}: {1}", new Object[]{id, exists});
        return exists;
    }
}
