/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;

import com.mycompany.csacoursework.model.Cart;
import com.mycompany.csacoursework.model.CartItem;
import com.mycompany.csacoursework.service.CartService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * pubic class for CartResourse
 *
 * @author Yasith
 */
@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private static final Logger LOGGER = Logger.getLogger(CartResource.class.getName());
    private CartService cartService = CartService.getInstance();

    /**
     * Get cart
     *
     * @param customerId
     * @return
     */
    @GET
    public Cart getCart(@PathParam("customerId") Long customerId) {
        LOGGER.log(Level.INFO, "Received request to get cart for customer with ID: {0}", customerId);
        return cartService.getCart(customerId);
    }

    /**
     * Create a cart
     *
     * @param customerId
     * @param cartItem
     * @return
     */
    @POST
    @Path("/items")
    public Cart addItemToCart(@PathParam("customerId") Long customerId, CartItem cartItem) {
        LOGGER.log(Level.INFO, "Received request to add item to cart for customer with ID: {0}", customerId);

        if (cartItem.getBookId() == null) {
            LOGGER.warning("Book ID is null");
            throw new BadRequestException("Book ID cannot be null");
        }

        if (cartItem.getQuantity() <= 0) {
            LOGGER.log(Level.WARNING, "Invalid quantity: {0}", cartItem.getQuantity());
            throw new BadRequestException("Quantity must be greater than zero");
        }

        return cartService.addItemToCart(customerId, cartItem.getBookId(), cartItem.getQuantity());
    }

    /**
     * Update cart
     *
     * @param customerId
     * @param bookId
     * @param cartItem
     * @return
     */
    @PUT
    @Path("/items/{bookId}")
    public Cart updateCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId,
            CartItem cartItem) {
        LOGGER.log(Level.INFO, "Received request to update item in cart for customer with ID: {0}", customerId);

        if (cartItem.getQuantity() <= 0) {
            LOGGER.log(Level.WARNING, "Invalid quantity: {0}", cartItem.getQuantity());
            throw new BadRequestException("Quantity must be greater than zero");
        }

        return cartService.updateItemQuantity(customerId, bookId, cartItem.getQuantity());
    }

    /**
     * Delete Cart
     *
     * @param customerId
     * @param bookId
     * @return
     */
    @DELETE
    @Path("/items/{bookId}")
    public Cart removeCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId) {
        LOGGER.log(Level.INFO, "Received request to remove item from cart for customer with ID: {0}", customerId);
        return cartService.removeItemFromCart(customerId, bookId);
    }

    /**
     * Clear cart
     *
     * @param customerId
     * @return
     */
    @DELETE
    public Response clearCart(@PathParam("customerId") Long customerId) {
        LOGGER.log(Level.INFO, "Received request to clear cart for customer with ID: {0}", customerId);
        cartService.clearCart(customerId);
        return Response.noContent().build();
    }
}
