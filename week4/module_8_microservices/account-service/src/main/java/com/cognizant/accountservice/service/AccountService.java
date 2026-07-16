package com.cognizant.accountservice.service;

import com.cognizant.accountservice.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AccountService - Business logic layer for account operations.
 *
 * Uses an in-memory Map as data store (simulates a database).
 * In a real microservice this would use a JPA repository.
 *
 * Operations:
 *   - getAllAccounts()
 *   - getAccountByNumber(accountNumber)
 *   - getAccountsByCustomerId(customerId)
 *   - createAccount(account)
 *   - updateAccount(accountNumber, account)
 *   - deleteAccount(accountNumber)
 */
@Service
public class AccountService {

    // In-memory data store: key = accountNumber
    private final Map<String, Account> accountStore = new HashMap<>();

    // -------------------------------------------------------
    // Seed with sample data on startup
    // -------------------------------------------------------
    public AccountService() {
        accountStore.put("ACC001", new Account("ACC001", "SAVINGS", "CUST001",
                "Prathibha S", 75000.00, "SBIN0001234", "Bangalore Main", "ACTIVE"));

        accountStore.put("ACC002", new Account("ACC002", "CURRENT", "CUST002",
                "Rahul Verma", 150000.00, "HDFC0005678", "Chennai Branch", "ACTIVE"));

        accountStore.put("ACC003", new Account("ACC003", "FIXED_DEPOSIT", "CUST001",
                "Prathibha S", 200000.00, "SBIN0001234", "Bangalore Main", "ACTIVE"));

        accountStore.put("ACC004", new Account("ACC004", "SAVINGS", "CUST003",
                "Meera Nair", 12000.00, "ICIC0009012", "Pune West", "INACTIVE"));
    }

    // -------------------------------------------------------
    // GET all accounts
    // -------------------------------------------------------
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountStore.values());
    }

    // -------------------------------------------------------
    // GET account by account number
    // -------------------------------------------------------
    public Account getAccountByNumber(String accountNumber) {
        return accountStore.get(accountNumber);
    }

    // -------------------------------------------------------
    // GET accounts by customer ID
    // -------------------------------------------------------
    public List<Account> getAccountsByCustomerId(String customerId) {
        List<Account> result = new ArrayList<>();
        for (Account acc : accountStore.values()) {
            if (acc.getCustomerId().equalsIgnoreCase(customerId)) {
                result.add(acc);
            }
        }
        return result;
    }

    // -------------------------------------------------------
    // CREATE a new account
    // -------------------------------------------------------
    public Account createAccount(Account account) {
        // Auto-generate accountNumber if not provided
        if (account.getAccountNumber() == null || account.getAccountNumber().isBlank()) {
            account.setAccountNumber("ACC" + System.currentTimeMillis());
        }
        if (account.getStatus() == null || account.getStatus().isBlank()) {
            account.setStatus("ACTIVE");
        }
        accountStore.put(account.getAccountNumber(), account);
        return account;
    }

    // -------------------------------------------------------
    // UPDATE an existing account
    // -------------------------------------------------------
    public Account updateAccount(String accountNumber, Account updatedAccount) {
        Account existing = accountStore.get(accountNumber);
        if (existing == null) return null;

        existing.setAccountType(updatedAccount.getAccountType());
        existing.setCustomerName(updatedAccount.getCustomerName());
        existing.setBalance(updatedAccount.getBalance());
        existing.setIfscCode(updatedAccount.getIfscCode());
        existing.setBranchName(updatedAccount.getBranchName());
        existing.setStatus(updatedAccount.getStatus());

        accountStore.put(accountNumber, existing);
        return existing;
    }

    // -------------------------------------------------------
    // DELETE / Close an account
    // -------------------------------------------------------
    public boolean deleteAccount(String accountNumber) {
        if (!accountStore.containsKey(accountNumber)) return false;
        accountStore.remove(accountNumber);
        return true;
    }
}
