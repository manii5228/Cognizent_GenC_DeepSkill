package com.cognizant.loanservice.controller;

import com.cognizant.loanservice.model.Loan;
import com.cognizant.loanservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoanController - REST Controller exposing Loan Microservice APIs.
 *
 * Base URL: /loans
 *
 * Endpoints:
 *   GET    /loans                             - Get all loans
 *   GET    /loans/{loanId}                   - Get loan by ID
 *   GET    /loans/customer/{customerId}       - Get all loans of a customer
 *   POST   /loans                             - Apply for a new loan
 *   PATCH  /loans/{loanId}/status            - Update loan status
 *   DELETE /loans/{loanId}                   - Delete a loan application
 *   GET    /loans/emi-calculator             - Calculate EMI
 */
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // -------------------------------------------------------
    // GET /loans  → List all loans
    // -------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    // -------------------------------------------------------
    // GET /loans/{loanId}  → Get single loan
    // -------------------------------------------------------
    @GetMapping("/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable String loanId) {
        Loan loan = loanService.getLoanById(loanId);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loan not found with ID: " + loanId);
        }
        return ResponseEntity.ok(loan);
    }

    // -------------------------------------------------------
    // GET /loans/customer/{customerId}  → Get by customer
    // -------------------------------------------------------
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(loanService.getLoansByCustomerId(customerId));
    }

    // -------------------------------------------------------
    // POST /loans  → Apply for a new loan
    // -------------------------------------------------------
    @PostMapping
    public ResponseEntity<Loan> applyLoan(@RequestBody Loan loan) {
        Loan created = loanService.applyLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------------------------------------------------------
    // PATCH /loans/{loanId}/status  → Update loan status
    // Request body: { "status": "APPROVED" }
    // -------------------------------------------------------
    @PatchMapping("/{loanId}/status")
    public ResponseEntity<?> updateLoanStatus(@PathVariable String loanId,
                                               @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest().body("'status' field is required in request body.");
        }
        Loan updated = loanService.updateLoanStatus(loanId, newStatus);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loan not found with ID: " + loanId);
        }
        return ResponseEntity.ok(updated);
    }

    // -------------------------------------------------------
    // DELETE /loans/{loanId}  → Delete loan application
    // -------------------------------------------------------
    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable String loanId) {
        boolean deleted = loanService.deleteLoan(loanId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loan not found with ID: " + loanId);
        }
        return ResponseEntity.ok("Loan " + loanId + " deleted successfully.");
    }

    // -------------------------------------------------------
    // GET /loans/emi-calculator
    //   Query params: principal, annualRate, tenureMonths
    //   Example: /loans/emi-calculator?principal=500000&annualRate=8.5&tenureMonths=60
    // -------------------------------------------------------
    @GetMapping("/emi-calculator")
    public ResponseEntity<Map<String, Object>> calculateEmi(
            @RequestParam double principal,
            @RequestParam double annualRate,
            @RequestParam int tenureMonths) {

        double emi = loanService.calculateEmi(principal, annualRate, tenureMonths);

        Map<String, Object> response = new HashMap<>();
        response.put("principal", principal);
        response.put("annualRatePercent", annualRate);
        response.put("tenureMonths", tenureMonths);
        response.put("monthlyEmi", emi);
        response.put("totalPayable", Math.round(emi * tenureMonths * 100.0) / 100.0);
        response.put("totalInterest", Math.round((emi * tenureMonths - principal) * 100.0) / 100.0);

        return ResponseEntity.ok(response);
    }
}
