/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework;

// Import Libraries...
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

/**
 * Main application class for start HTTP server
 *
 * @author Yasith
 */
public class Main {

    // Logger instance for logger class
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    // Base URI the Grizzly HTTP server will listen on port address 8080
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Create a resource config that scans for JAX-RS resources and providers
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.mycompany.csacoursework.resource", "com.mycompany.csacoursework.exception")
                .register(new JacksonFeature())
                .register(new ObjectMapperContextResolver());

        // Log server startup information
        LOGGER.info("Starting server at " + BASE_URI);
        // Start new instance of Grizzly HTTP server
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * ObjectMapper configuration for handling Java 8 date/time types.
     */
    static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper mapper;

        public ObjectMapperContextResolver() {
            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        }

        /**
         *
         * @param type
         * @return object mapper instance
         */
        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }
    }

    /**
     * Main method. Start the server and wait for user input to Shut Down the
     * server
     *
     * @param args command line arguments
     * @throws IOException if there's an I/O error
     */
    public static void main(String[] args) throws IOException {
        // Start the http server
        final HttpServer server = startServer();

        // Log for successfull server startup
        LOGGER.info("Bookstore API server started at " + BASE_URI);
        LOGGER.info("Press Enter to stop the server...");

        // wait for user input for shutdown
        System.in.read();

        // shutdown the server
        server.shutdownNow();
        LOGGER.info("Server shut down");
    }
}
