/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;

// Import Libraries
import com.mycompany.csacoursework.model.Customer;
import com.mycompany.csacoursework.service.CustomerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * public class for Customer Resource
 *
 * @author Yasith
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static final Logger LOGGER = Logger.getLogger(CustomerResource.class.getName());
    private CustomerService customerService = CustomerService.getInstance();

    /**
     * create customer
     *
     * @param customer
     * @param uriInfo
     * @return
     */
    @POST
    public Response createCustomer(Customer customer, @Context UriInfo uriInfo) {
        LOGGER.log(Level.INFO, "Received request to create a new customer: {0} {1}", new Object[]{customer.getFirstName(), customer.getLastName()});

        Customer createdCustomer = customerService.createCustomer(customer);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdCustomer.getId())).build();
        LOGGER.log(Level.INFO, "Customer created successfully with ID: {0}", createdCustomer.getId());
        return Response.created(uri).entity(createdCustomer).build();
    }

    /**
     * get all the customers
     *
     * @return
     */
    @GET
    public List<Customer> getAllCustomers() {
        LOGGER.info("Received request to get all customers");
        return customerService.getAllCustomers();
    }

    /**
     * get customer by id
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to get customer with ID: {0}", id);
        return customerService.getCustomerById(id);
    }

    /**
     * Update customer
     *
     * @param id
     * @param customer
     * @return
     */
    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id") Long id, Customer customer) {
        LOGGER.log(Level.INFO, "Received request to update customer with ID: {0}", id);

        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        LOGGER.log(Level.INFO, "Customer updated successfully with ID: {0}", id);
        return updatedCustomer;
    }

    /**
     * delete customer
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to delete customer with ID: {0}", id);
        customerService.deleteCustomer(id);
        LOGGER.log(Level.INFO, "Customer deleted successfully with ID: {0}", id);
        return Response.noContent().build();
    }
}
