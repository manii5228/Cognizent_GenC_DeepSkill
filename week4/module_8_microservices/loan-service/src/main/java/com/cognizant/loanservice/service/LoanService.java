package com.cognizant.loanservice.service;

import com.cognizant.loanservice.model.Loan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoanService - Business logic layer for loan operations.
 *
 * Uses an in-memory Map as data store (simulates a database).
 * In a real microservice this would use a JPA repository.
 *
 * Includes EMI calculation formula:
 *   EMI = [P × r × (1+r)^n] / [(1+r)^n − 1]
 *   where P = principal, r = monthly rate, n = tenure in months
 *
 * Operations:
 *   - getAllLoans()
 *   - getLoanById(loanId)
 *   - getLoansByCustomerId(customerId)
 *   - applyLoan(loan)
 *   - updateLoanStatus(loanId, status)
 *   - deleteLoan(loanId)
 *   - calculateEmi(principal, annualRate, tenureMonths)
 */
@Service
public class LoanService {

    // In-memory data store: key = loanId
    private final Map<String, Loan> loanStore = new HashMap<>();

    // -------------------------------------------------------
    // Seed with sample data on startup
    // -------------------------------------------------------
    public LoanService() {
        loanStore.put("LOAN001", new Loan("LOAN001", "HOME", "CUST001",
                "Prathibha S", 2500000.00, 2100000.00, 8.5, 240,
                calculateEmi(2500000.00, 8.5, 240), "APPROVED"));

        loanStore.put("LOAN002", new Loan("LOAN002", "CAR", "CUST002",
                "Rahul Verma", 800000.00, 600000.00, 9.5, 60,
                calculateEmi(800000.00, 9.5, 60), "APPROVED"));

        loanStore.put("LOAN003", new Loan("LOAN003", "PERSONAL", "CUST003",
                "Meera Nair", 300000.00, 300000.00, 12.0, 36,
                calculateEmi(300000.00, 12.0, 36), "PENDING"));

        loanStore.put("LOAN004", new Loan("LOAN004", "EDUCATION", "CUST001",
                "Prathibha S", 500000.00, 500000.00, 7.0, 84,
                calculateEmi(500000.00, 7.0, 84), "APPROVED"));
    }

    // -------------------------------------------------------
    // GET all loans
    // -------------------------------------------------------
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loanStore.values());
    }

    // -------------------------------------------------------
    // GET loan by ID
    // -------------------------------------------------------
    public Loan getLoanById(String loanId) {
        return loanStore.get(loanId);
    }

    // -------------------------------------------------------
    // GET loans by customer ID
    // -------------------------------------------------------
    public List<Loan> getLoansByCustomerId(String customerId) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loanStore.values()) {
            if (loan.getCustomerId().equalsIgnoreCase(customerId)) {
                result.add(loan);
            }
        }
        return result;
    }

    // -------------------------------------------------------
    // APPLY for a new loan (CREATE)
    // -------------------------------------------------------
    public Loan applyLoan(Loan loan) {
        // Auto-generate loanId if not provided
        if (loan.getLoanId() == null || loan.getLoanId().isBlank()) {
            loan.setLoanId("LOAN" + System.currentTimeMillis());
        }
        // Default status = PENDING on new application
        loan.setStatus("PENDING");

        // Calculate EMI automatically
        if (loan.getPrincipalAmount() > 0 && loan.getInterestRate() > 0 && loan.getTenureMonths() > 0) {
            loan.setEmiAmount(calculateEmi(loan.getPrincipalAmount(),
                    loan.getInterestRate(), loan.getTenureMonths()));
        }
        // Outstanding = full principal on new loan
        loan.setOutstandingAmount(loan.getPrincipalAmount());

        loanStore.put(loan.getLoanId(), loan);
        return loan;
    }

    // -------------------------------------------------------
    // UPDATE loan status (APPROVED / REJECTED / CLOSED)
    // -------------------------------------------------------
    public Loan updateLoanStatus(String loanId, String newStatus) {
        Loan loan = loanStore.get(loanId);
        if (loan == null) return null;
        loan.setStatus(newStatus.toUpperCase());
        loanStore.put(loanId, loan);
        return loan;
    }

    // -------------------------------------------------------
    // DELETE a loan application
    // -------------------------------------------------------
    public boolean deleteLoan(String loanId) {
        if (!loanStore.containsKey(loanId)) return false;
        loanStore.remove(loanId);
        return true;
    }

    // -------------------------------------------------------
    // CALCULATE EMI using standard formula:
    //   EMI = [P × r × (1+r)^n] / [(1+r)^n − 1]
    //   P = principal, r = monthly interest rate, n = tenure months
    // -------------------------------------------------------
    public double calculateEmi(double principal, double annualRatePercent, int tenureMonths) {
        double monthlyRate = annualRatePercent / (12 * 100);
        double power = Math.pow(1 + monthlyRate, tenureMonths);
        double emi = (principal * monthlyRate * power) / (power - 1);
        // Round to 2 decimal places
        return Math.round(emi * 100.0) / 100.0;
    }
}
