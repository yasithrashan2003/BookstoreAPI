/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;


import com.mycompany.csacoursework.model.Customer;
import com.mycompany.csacoursework.service.CustomerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;


/**
 *
 * @author Yasith
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static final Logger LOGGER = Logger.getLogger(CustomerResource.class.getName());
    private CustomerService customerService = CustomerService.getInstance();
    
    @POST
    public Response createCustomer(Customer customer, @Context UriInfo uriInfo) {
        LOGGER.info("Received request to create a new customer: " + customer.getFirstName() + " " + customer.getLastName());
        
        Customer createdCustomer = customerService.createCustomer(customer);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdCustomer.getId())).build();
        LOGGER.info("Customer created successfully with ID: " + createdCustomer.getId());
        return Response.created(uri).entity(createdCustomer).build();
    }
    
    @GET
    public List<Customer> getAllCustomers() {
        LOGGER.info("Received request to get all customers");
        return customerService.getAllCustomers();
    }
    
    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") Long id) {
        LOGGER.info("Received request to get customer with ID: " + id);
        return customerService.getCustomerById(id);
    }
    
    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id") Long id, Customer customer) {
        LOGGER.info("Received request to update customer with ID: " + id);
        
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        LOGGER.info("Customer updated successfully with ID: " + id);
        return updatedCustomer;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        LOGGER.info("Received request to delete customer with ID: " + id);
        customerService.deleteCustomer(id);
        LOGGER.info("Customer deleted successfully with ID: " + id);
        return Response.noContent().build();
    }
}
