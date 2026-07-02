package com.library.repository;

/**
 * BookRepository is responsible for data access operations related to books.
 * In a real application, this class would interact with a database.
 * Exercise 1 & 2: Defined as a Spring bean in applicationContext.xml and
 * injected into BookService via Dependency Injection.
 */
public class BookRepository {

    /**
     * Simulates fetching all books from a data store.
     */
    public void findAllBooks() {
        System.out.println("[BookRepository] Fetching all books from the repository...");
    }

    /**
     * Simulates saving a book to the data store.
     *
     * @param title the title of the book to save
     */
    public void saveBook(String title) {
        System.out.println("[BookRepository] Saving book: \"" + title + "\" to the repository.");
    }
}
