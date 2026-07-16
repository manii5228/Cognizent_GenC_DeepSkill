package com.cognizant.accountservice.controller;

import com.cognizant.accountservice.model.Account;
import com.cognizant.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AccountController - REST Controller exposing Account Microservice APIs.
 *
 * Base URL: /accounts
 *
 * Endpoints:
 *   GET    /accounts                          - Get all accounts
 *   GET    /accounts/{accountNumber}          - Get account by account number
 *   GET    /accounts/customer/{customerId}    - Get all accounts of a customer
 *   POST   /accounts                          - Create a new account
 *   PUT    /accounts/{accountNumber}          - Update an account
 *   DELETE /accounts/{accountNumber}          - Delete/Close an account
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // -------------------------------------------------------
    // GET /accounts  → List all accounts
    // -------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // -------------------------------------------------------
    // GET /accounts/{accountNumber}  → Get single account
    // -------------------------------------------------------
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccountByNumber(@PathVariable String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found with number: " + accountNumber);
        }
        return ResponseEntity.ok(account);
    }

    // -------------------------------------------------------
    // GET /accounts/customer/{customerId}  → Get by customer
    // -------------------------------------------------------
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable String customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    // -------------------------------------------------------
    // POST /accounts  → Create new account
    // -------------------------------------------------------
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------------------------------------------------------
    // PUT /accounts/{accountNumber}  → Update account
    // -------------------------------------------------------
    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable String accountNumber,
                                            @RequestBody Account account) {
        Account updated = accountService.updateAccount(accountNumber, account);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found with number: " + accountNumber);
        }
        return ResponseEntity.ok(updated);
    }

    // -------------------------------------------------------
    // DELETE /accounts/{accountNumber}  → Delete account
    // -------------------------------------------------------
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        boolean deleted = accountService.deleteAccount(accountNumber);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account not found with number: " + accountNumber);
        }
        return ResponseEntity.ok("Account " + accountNumber + " deleted successfully.");
    }
}
