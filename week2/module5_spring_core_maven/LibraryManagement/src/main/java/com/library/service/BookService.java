package com.library.service;

import com.library.repository.BookRepository;

/**
 * BookService handles the business logic for library book operations.
 * Exercise 1: Defined as a Spring bean in applicationContext.xml.
 * Exercise 2: BookRepository is injected using Spring's setter-based
 *             Dependency Injection (IoC principle).
 */
public class BookService {

    // Dependency on BookRepository – injected by Spring IoC container
    private BookRepository bookRepository;

    /**
     * Setter method used by Spring to inject the BookRepository dependency.
     * Corresponds to the <property name="bookRepository" ref="bookRepository"/>
     * configuration in applicationContext.xml.
     *
     * @param bookRepository the BookRepository instance to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] BookRepository has been injected via setter DI.");
    }

    /**
     * Retrieves and displays all available books.
     * Delegates the data-fetching responsibility to BookRepository.
     */
    public void displayAllBooks() {
        System.out.println("[BookService] Displaying all books...");
        bookRepository.findAllBooks();
    }

    /**
     * Adds a new book to the library.
     * Delegates the persistence responsibility to BookRepository.
     *
     * @param title the title of the book to add
     */
    public void addBook(String title) {
        System.out.println("[BookService] Adding a new book: \"" + title + "\"");
        bookRepository.saveBook(title);
    }
}
