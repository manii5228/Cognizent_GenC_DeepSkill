package com.cognizant.accountservice.model;

/**
 * Account - Domain model representing a bank account.
 *
 * Fields:
 *   accountNumber  - Unique identifier for the account
 *   accountType    - Type: SAVINGS, CURRENT, FIXED_DEPOSIT
 *   customerId     - ID of the owning customer
 *   customerName   - Full name of the account holder
 *   balance        - Current available balance
 *   ifscCode       - IFSC code of the branch
 *   branchName     - Bank branch name
 *   status         - ACTIVE / INACTIVE / CLOSED
 */
public class Account {

    private String accountNumber;
    private String accountType;   // SAVINGS, CURRENT, FIXED_DEPOSIT
    private String customerId;
    private String customerName;
    private double balance;
    private String ifscCode;
    private String branchName;
    private String status;        // ACTIVE, INACTIVE, CLOSED

    // -------------------------------------------------------
    // Constructors
    // -------------------------------------------------------
    public Account() {}

    public Account(String accountNumber, String accountType, String customerId,
                   String customerName, double balance, String ifscCode,
                   String branchName, String status) {
        this.accountNumber = accountNumber;
        this.accountType   = accountType;
        this.customerId    = customerId;
        this.customerName  = customerName;
        this.balance       = balance;
        this.ifscCode      = ifscCode;
        this.branchName    = branchName;
        this.status        = status;
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
    public String getAccountNumber() { return accountNumber; }
    public void   setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void   setAccountType(String accountType) { this.accountType = accountType; }

    public String getCustomerId() { return customerId; }
    public void   setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void   setCustomerName(String customerName) { this.customerName = customerName; }

    public double getBalance() { return balance; }
    public void   setBalance(double balance) { this.balance = balance; }

    public String getIfscCode() { return ifscCode; }
    public void   setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getBranchName() { return branchName; }
    public void   setBranchName(String branchName) { this.branchName = branchName; }

    public String getStatus() { return status; }
    public void   setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", balance=" + balance +
                ", ifscCode='" + ifscCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
