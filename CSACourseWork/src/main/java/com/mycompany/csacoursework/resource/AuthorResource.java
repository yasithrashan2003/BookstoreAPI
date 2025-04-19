/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;
import com.mycompany.csacoursework.model.Author;
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
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
     private static final Logger LOGGER = Logger.getLogger(AuthorResource.class.getName());
    // Use singleton instance
    private AuthorService authorService = AuthorService.getInstance();
    private BookService bookService = BookService.getInstance(); // Assuming BookService also has a getInstance method
    
    @POST
    public Response createAuthor(Author author, @Context UriInfo uriInfo) {
        LOGGER.info("Received request to create a new author: " + author.getFirstName() + " " + author.getLastName());
        
        Author createdAuthor = authorService.createAuthor(author);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdAuthor.getId())).build();
        LOGGER.info("Author created successfully with ID: " + createdAuthor.getId());
        return Response.created(uri).entity(createdAuthor).build();
    }
    
    @GET
    public List<Author> getAllAuthors() {
        LOGGER.info("Received request to get all authors");
        return authorService.getAllAuthors();
    }
    
    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") Long id) {
        LOGGER.info("Received request to get author with ID: " + id);
        return authorService.getAuthorById(id);
    }
    
    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") Long id, Author author) {
        LOGGER.info("Received request to update author with ID: " + id);
        
        Author updatedAuthor = authorService.updateAuthor(id, author);
        LOGGER.info("Author updated successfully with ID: " + id);
        return updatedAuthor;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        LOGGER.info("Received request to delete author with ID: " + id);
        authorService.deleteAuthor(id);
        LOGGER.info("Author deleted successfully with ID: " + id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/books")
    public List<Book> getAuthorBooks(@PathParam("id") Long id) {
        LOGGER.info("Received request to get books for author with ID: " + id);
        // Verify author exists first
        authorService.getAuthorById(id);
        List<Book> books = bookService.getBooksByAuthor(id);
        LOGGER.info("Found " + books.size() + " books for author with ID: " + id);
        return books;
    }
}