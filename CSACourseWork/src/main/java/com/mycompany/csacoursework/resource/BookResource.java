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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Public class for BookResourse
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

    /**
     * Create a Book
     *
     * @param book
     * @param uriInfo
     * @return
     */
    @POST
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        LOGGER.log(Level.INFO, "Received request to create a new book: {0}", book.getTitle());

        // Validate author exists
        if (!authorService.authorExists(book.getAuthorId())) {
            LOGGER.log(Level.WARNING, "Author not found with ID: {0}", book.getAuthorId());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Author Not Found\", \"message\": \"Author with ID " + book.getAuthorId() + " does not exist.\"}")
                    .build();
        }

        Book createdBook = bookService.createBook(book);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdBook.getId())).build();
        LOGGER.log(Level.INFO, "Book created successfully with ID: {0}", createdBook.getId());
        return Response.created(uri).entity(createdBook).build();
    }

    /**
     * get all the books
     *
     * @return
     */
    @GET
    public List<Book> getAllBooks() {
        LOGGER.info("Received request to get all books");
        return bookService.getAllBooks();
    }

    /**
     * get specific book by id
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to get book with ID: {0}", id);
        return bookService.getBookById(id);
    }

    /**
     * Update a book
     *
     * @param id
     * @param book
     * @return
     */
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        LOGGER.log(Level.INFO, "Received request to update book with ID: {0}", id);

        // Validate author exists or not
        if (!authorService.authorExists(book.getAuthorId())) {
            LOGGER.log(Level.WARNING, "Author not found with ID: {0}", book.getAuthorId());
            throw new NotFoundException("Author with ID " + book.getAuthorId() + " does not exist");
        }

        Book updatedBook = bookService.updateBook(id, book);
        LOGGER.log(Level.INFO, "Book updated successfully with ID: {0}", id);
        return updatedBook;
    }

    /**
     * delete a book
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "Received request to delete book with ID: {0}", id);
        bookService.deleteBook(id);
        LOGGER.log(Level.INFO, "Book deleted successfully with ID: {0}", id);
        return Response.noContent().build();
    }

}
