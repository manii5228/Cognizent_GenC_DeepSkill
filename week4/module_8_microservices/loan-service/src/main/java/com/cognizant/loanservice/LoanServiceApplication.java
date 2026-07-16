package com.cognizant.loanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LoanServiceApplication - Main entry point for the Loan Microservice.
 *
 * This microservice handles all loan-related operations:
 *   - Apply for a loan
 *   - Retrieve loan details by loan ID
 *   - Get all loans of a customer
 *   - Update loan status (APPROVED / REJECTED / CLOSED)
 *   - Delete a loan application
 *
 * Runs on port 8082.
 */
@SpringBootApplication
public class LoanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Loan Microservice started on port 8082     ");
        System.out.println("  REST API: http://localhost:8082/loans       ");
        System.out.println("  Health:   http://localhost:8082/actuator/health");
        System.out.println("==============================================");
    }
}
