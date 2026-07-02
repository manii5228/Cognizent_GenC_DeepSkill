package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * LibraryManagementApplication serves as the entry point of the application.
 *
 * Exercise 1: Loads the Spring ApplicationContext from applicationContext.xml
 *             and retrieves the BookService bean to verify context setup.
 * Exercise 2: Verifies that BookRepository has been correctly injected into
 *             BookService through Spring's Dependency Injection.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        System.out.println("=== Library Management Application Starting ===\n");

        // Load the Spring Application Context from the XML configuration file
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[App] Spring ApplicationContext loaded successfully.");
        System.out.println("[App] Retrieving 'bookService' bean from context...\n");

        // Retrieve the BookService bean – Spring will have already injected BookRepository
        BookService bookService = (BookService) context.getBean("bookService");

        System.out.println("\n--- Testing BookService Operations ---\n");

        // Exercise 1 & 2: Test service methods which internally use the injected repository
        bookService.displayAllBooks();
        System.out.println();

        bookService.addBook("Clean Code by Robert C. Martin");
        System.out.println();

        bookService.addBook("Spring in Action by Craig Walls");
        System.out.println();

        System.out.println("=== Library Management Application Finished ===");

        // Close the context to release resources
        ((ClassPathXmlApplicationContext) context).close();
    }
}
