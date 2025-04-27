/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;

// import libraries
import com.mycompany.csacoursework.model.Order;
import com.mycompany.csacoursework.service.OrderService;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * public class for Order Resource
 *
 * @author Yasith
 */
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOGGER = Logger.getLogger(OrderResource.class.getName());
    private OrderService orderService = OrderService.getInstance();

    /**
     * create order
     *
     * @param customerId
     * @param uriInfo
     * @return
     */
    @POST
    public Response createOrder(@PathParam("customerId") Long customerId, @Context UriInfo uriInfo) {
        LOGGER.log(Level.INFO, "Received request to create order for customer with ID: {0}", customerId);

        Order createdOrder = orderService.createOrder(customerId);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdOrder.getId())).build();
        LOGGER.log(Level.INFO, "Order created successfully with ID: {0}", createdOrder.getId());
        return Response.created(uri).entity(createdOrder).build();
    }

    /**
     * get order
     *
     * @param customerId
     * @return
     */
    @GET
    public List<Order> getCustomerOrders(@PathParam("customerId") Long customerId) {
        LOGGER.log(Level.INFO, "Received request to get orders for customer with ID: {0}", customerId);
        return orderService.getCustomerOrders(customerId);
    }

    /**
     * get order by id
     *
     * @param customerId
     * @param orderId
     * @return
     */
    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId) {
        LOGGER.log(Level.INFO, "Received request to get order with ID: {0} for customer with ID: {1}", new Object[]{orderId, customerId});
        return orderService.getOrder(customerId, orderId);
    }
}
