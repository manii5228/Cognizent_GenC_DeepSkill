package com.cognizant.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AccountServiceApplication - Main entry point for the Account Microservice.
 *
 * This microservice handles all account-related operations:
 *   - Create a new bank account
 *   - Retrieve account details by account number
 *   - Get all accounts for a customer
 *   - Update account details
 *   - Delete/Close an account
 *
 * Runs on port 8081.
 */
@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Account Microservice started on port 8081  ");
        System.out.println("  REST API: http://localhost:8081/accounts    ");
        System.out.println("  Health:   http://localhost:8081/actuator/health");
        System.out.println("==============================================");
    }
}
