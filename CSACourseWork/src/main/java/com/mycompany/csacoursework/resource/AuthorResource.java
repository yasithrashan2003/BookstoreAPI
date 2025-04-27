/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csacoursework.resource;

// Import Libraries
import com.mycompany.csacoursework.model.Author;
import com.mycompany.csacoursework.model.Book;
import com.mycompany.csacoursework.service.AuthorService;
import com.mycompany.csacoursework.service.BookService;
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
 * public class for AuthorResource
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
    private BookService bookService = BookService.getInstance(); 

    /**
     * POST method
     *
     * @param author
     * @param uriInfo
     * @return
     */
    @POST
    public Response createAuthor(Author author, @Context UriInfo uriInfo) {
        LOGGER.log(Level.INFO, "Received request to create a new author: {0} {1}", new Object[]{author.getFirstName(), author.getLastName()});

        Author createdAuthor = authorService.createAuthor(author);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdAuthor.getId())).build();
        LOGGER.log(Level.INFO, "Author created successfully with ID: {0}", createdAuthor.getId());
        return Response.created(uri).entity(createdAuthor).build();
    }

    /**
     * Get all the authors
     *
     * @return
     */
    @GET
    public List<Author> getAllAuthors() {
        LOGGER.info("Received request to get all authors");
        return authorService.getAllAuthors();
    }

    /**
     * Get specific author
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to get author with ID: {0}", id);
        return authorService.getAuthorById(id);
    }

    /**
     * Update Author
     *
     * @param id
     * @param author
     * @return
     */
    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") Long id, Author author) {
        LOGGER.log(Level.INFO, "Received request to update author with ID: {0}", id);

        Author updatedAuthor = authorService.updateAuthor(id, author);
        LOGGER.log(Level.INFO, "Author updated successfully with ID: {0}", id);
        return updatedAuthor;
    }

    /**
     * Delete Author
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to delete author with ID: {0}", id);
        authorService.deleteAuthor(id);
        LOGGER.log(Level.INFO, "Author deleted successfully with ID: {0}", id);
        return Response.noContent().build();
    }

    /**
     * Get authors by book
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}/books")
    public List<Book> getAuthorBooks(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to get books for author with ID: {0}", id);
        // Verify author exists first
        authorService.getAuthorById(id);
        List<Book> books = bookService.getBooksByAuthor(id);
        LOGGER.log(Level.INFO, "Found {0} books for author with ID: {1}", new Object[]{books.size(), id});
        return books;
    }
}
