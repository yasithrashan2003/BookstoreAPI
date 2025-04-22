/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception mapper for convert CustomerNotFound to HTTP response
 *
 * @author Yasith
 */
@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {

    /**
     * Convert exception in to HTTP response
     *
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(CustomerNotFoundException exception) {

        // create map for hold error details
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Customer Not Found");
        errorResponse.put("message", exception.getMessage());

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
