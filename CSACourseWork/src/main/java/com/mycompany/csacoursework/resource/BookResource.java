/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;

import com.mycompany.csacoursework.model.Book;
import com.mycompany.csacoursework.service.AuthorService;
import com.mycompany.csacoursework.service.BookService;
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
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private static final Logger LOGGER = Logger.getLogger(BookResource.class.getName());
    private BookService bookService = BookService.getInstance();
private AuthorService authorService = AuthorService.getInstance();
    
    @POST
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        LOGGER.info("Received request to create a new book: " + book.getTitle());
        
        // Validate author exists
        if (!authorService.authorExists(book.getAuthorId())) {
            LOGGER.warning("Author not found with ID: " + book.getAuthorId());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Author Not Found\", \"message\": \"Author with ID " + book.getAuthorId() + " does not exist.\"}")
                    .build();
        }
        
        Book createdBook = bookService.createBook(book);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdBook.getId())).build();
        LOGGER.info("Book created successfully with ID: " + createdBook.getId());
        return Response.created(uri).entity(createdBook).build();
    }
    
    @GET
    public List<Book> getAllBooks() {
        LOGGER.info("Received request to get all books");
        return bookService.getAllBooks();
    }
    
    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") Long id) {
        LOGGER.info("Received request to get book with ID: " + id);
        return bookService.getBookById(id);
    }
    
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        LOGGER.info("Received request to update book with ID: " + id);
        
        // Validate author exists
        if (!authorService.authorExists(book.getAuthorId())) {
            LOGGER.warning("Author not found with ID: " + book.getAuthorId());
            throw new NotFoundException("Author with ID " + book.getAuthorId() + " does not exist");
        }
        
        Book updatedBook = bookService.updateBook(id, book);
        LOGGER.info("Book updated successfully with ID: " + id);
        return updatedBook;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        LOGGER.info("Received request to delete book with ID: " + id);
        bookService.deleteBook(id);
        LOGGER.info("Book deleted successfully with ID: " + id);
        return Response.noContent().build();
    }
    
}
