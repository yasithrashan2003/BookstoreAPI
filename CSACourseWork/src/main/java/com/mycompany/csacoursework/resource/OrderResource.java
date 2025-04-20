/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;

import com.mycompany.csacoursework.model.Order;
import com.mycompany.csacoursework.service.OrderService;
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
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    private static final Logger LOGGER = Logger.getLogger(OrderResource.class.getName());
    private OrderService orderService = OrderService.getInstance();
    
    @POST
    public Response createOrder(@PathParam("customerId") Long customerId, @Context UriInfo uriInfo) {
        LOGGER.info("Received request to create order for customer with ID: " + customerId);
        
        Order createdOrder = orderService.createOrder(customerId);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdOrder.getId())).build();
        LOGGER.info("Order created successfully with ID: " + createdOrder.getId());
        return Response.created(uri).entity(createdOrder).build();
    }
    
    @GET
    public List<Order> getCustomerOrders(@PathParam("customerId") Long customerId) {
        LOGGER.info("Received request to get orders for customer with ID: " + customerId);
        return orderService.getCustomerOrders(customerId);
    }
    
    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId) {
        LOGGER.info("Received request to get order with ID: " + orderId + " for customer with ID: " + customerId);
        return orderService.getOrder(customerId, orderId);
    }
}
