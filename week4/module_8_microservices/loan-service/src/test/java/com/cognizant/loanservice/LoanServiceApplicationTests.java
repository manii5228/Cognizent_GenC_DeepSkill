package com.cognizant.loanservice;

import com.cognizant.loanservice.controller.LoanController;
import com.cognizant.loanservice.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LoanServiceApplicationTests - Integration tests for Loan Microservice.
 *
 * Verifies:
 *   - Application context loads successfully
 *   - LoanController bean is present
 *   - LoanService bean is present
 *   - EMI calculation is correct
 *   - Initial seed data is available
 */
@SpringBootTest
class LoanServiceApplicationTests {

    @Autowired
    private LoanController loanController;

    @Autowired
    private LoanService loanService;

    // -------------------------------------------------------
    // Test 1: Context loads
    // -------------------------------------------------------
    @Test
    void contextLoads() {
        assertThat(loanController).isNotNull();
        assertThat(loanService).isNotNull();
    }

    // -------------------------------------------------------
    // Test 2: Seeded data is available
    // -------------------------------------------------------
    @Test
    void seedDataLoaded() {
        assertThat(loanService.getAllLoans()).isNotEmpty();
        assertThat(loanService.getAllLoans().size()).isGreaterThanOrEqualTo(4);
    }

    // -------------------------------------------------------
    // Test 3: Get loan by ID
    // -------------------------------------------------------
    @Test
    void getLoanById() {
        var loan = loanService.getLoanById("LOAN001");
        assertThat(loan).isNotNull();
        assertThat(loan.getLoanType()).isEqualTo("HOME");
        assertThat(loan.getStatus()).isEqualTo("APPROVED");
    }

    // -------------------------------------------------------
    // Test 4: Get loans by customer ID
    // -------------------------------------------------------
    @Test
    void getLoansByCustomerId() {
        var loans = loanService.getLoansByCustomerId("CUST001");
        assertThat(loans).hasSize(2); // LOAN001 and LOAN004
    }

    // -------------------------------------------------------
    // Test 5: EMI calculation
    //   For P=500000, rate=8.5%, tenure=60 months
    //   Expected EMI ≈ 10224.13
    // -------------------------------------------------------
    @Test
    void emiCalculationIsCorrect() {
        double emi = loanService.calculateEmi(500000, 8.5, 60);
        // EMI should be roughly 10224 (allow small margin)
        assertThat(emi).isBetween(10200.0, 10300.0);
    }

    // -------------------------------------------------------
    // Test 6: Apply new loan sets status to PENDING
    // -------------------------------------------------------
    @Test
    void applyLoanSetsPendingStatus() {
        var loan = new com.cognizant.loanservice.model.Loan();
        loan.setLoanType("PERSONAL");
        loan.setCustomerId("CUST999");
        loan.setCustomerName("Test User");
        loan.setPrincipalAmount(100000);
        loan.setInterestRate(10.0);
        loan.setTenureMonths(12);

        var created = loanService.applyLoan(loan);
        assertThat(created.getStatus()).isEqualTo("PENDING");
        assertThat(created.getEmiAmount()).isGreaterThan(0);
    }
}
