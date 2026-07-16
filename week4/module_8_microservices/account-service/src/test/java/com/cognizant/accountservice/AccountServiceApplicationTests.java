package com.cognizant.accountservice;

import com.cognizant.accountservice.controller.AccountController;
import com.cognizant.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AccountServiceApplicationTests - Integration tests for Account Microservice.
 *
 * Verifies:
 *   - Application context loads successfully
 *   - AccountController bean is present
 *   - AccountService bean is present
 *   - Initial seed data is available
 */
@SpringBootTest
class AccountServiceApplicationTests {

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountService accountService;

    // -------------------------------------------------------
    // Test 1: Context loads
    // -------------------------------------------------------
    @Test
    void contextLoads() {
        assertThat(accountController).isNotNull();
        assertThat(accountService).isNotNull();
    }

    // -------------------------------------------------------
    // Test 2: Seeded data is available
    // -------------------------------------------------------
    @Test
    void seedDataLoaded() {
        assertThat(accountService.getAllAccounts()).isNotEmpty();
        assertThat(accountService.getAllAccounts().size()).isGreaterThanOrEqualTo(4);
    }

    // -------------------------------------------------------
    // Test 3: Get account by number
    // -------------------------------------------------------
    @Test
    void getAccountByNumber() {
        var account = accountService.getAccountByNumber("ACC001");
        assertThat(account).isNotNull();
        assertThat(account.getCustomerName()).isEqualTo("Prathibha S");
    }

    // -------------------------------------------------------
    // Test 4: Get accounts by customer ID
    // -------------------------------------------------------
    @Test
    void getAccountsByCustomerId() {
        var accounts = accountService.getAccountsByCustomerId("CUST001");
        assertThat(accounts).hasSize(2); // ACC001 and ACC003
    }
}
