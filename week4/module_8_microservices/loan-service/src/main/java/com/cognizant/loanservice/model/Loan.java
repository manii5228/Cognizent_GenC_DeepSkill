package com.cognizant.loanservice.model;

/**
 * Loan - Domain model representing a bank loan.
 *
 * Fields:
 *   loanId         - Unique identifier for the loan
 *   loanType       - Type: HOME, CAR, PERSONAL, EDUCATION, BUSINESS
 *   customerId     - ID of the loan applicant (customer)
 *   customerName   - Full name of the loan applicant
 *   principalAmount - Total loan amount sanctioned
 *   outstandingAmount - Remaining amount to be repaid
 *   interestRate   - Annual interest rate (%)
 *   tenureMonths   - Loan tenure in months
 *   emiAmount      - Monthly EMI amount
 *   status         - PENDING / APPROVED / REJECTED / CLOSED
 */
public class Loan {

    private String loanId;
    private String loanType;          // HOME, CAR, PERSONAL, EDUCATION, BUSINESS
    private String customerId;
    private String customerName;
    private double principalAmount;
    private double outstandingAmount;
    private double interestRate;      // Annual %
    private int    tenureMonths;
    private double emiAmount;
    private String status;            // PENDING, APPROVED, REJECTED, CLOSED

    // -------------------------------------------------------
    // Constructors
    // -------------------------------------------------------
    public Loan() {}

    public Loan(String loanId, String loanType, String customerId, String customerName,
                double principalAmount, double outstandingAmount, double interestRate,
                int tenureMonths, double emiAmount, String status) {
        this.loanId            = loanId;
        this.loanType          = loanType;
        this.customerId        = customerId;
        this.customerName      = customerName;
        this.principalAmount   = principalAmount;
        this.outstandingAmount = outstandingAmount;
        this.interestRate      = interestRate;
        this.tenureMonths      = tenureMonths;
        this.emiAmount         = emiAmount;
        this.status            = status;
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
    public String getLoanId() { return loanId; }
    public void   setLoanId(String loanId) { this.loanId = loanId; }

    public String getLoanType() { return loanType; }
    public void   setLoanType(String loanType) { this.loanType = loanType; }

    public String getCustomerId() { return customerId; }
    public void   setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void   setCustomerName(String customerName) { this.customerName = customerName; }

    public double getPrincipalAmount() { return principalAmount; }
    public void   setPrincipalAmount(double principalAmount) { this.principalAmount = principalAmount; }

    public double getOutstandingAmount() { return outstandingAmount; }
    public void   setOutstandingAmount(double outstandingAmount) { this.outstandingAmount = outstandingAmount; }

    public double getInterestRate() { return interestRate; }
    public void   setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public int  getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }

    public double getEmiAmount() { return emiAmount; }
    public void   setEmiAmount(double emiAmount) { this.emiAmount = emiAmount; }

    public String getStatus() { return status; }
    public void   setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", loanType='" + loanType + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", principalAmount=" + principalAmount +
                ", outstandingAmount=" + outstandingAmount +
                ", interestRate=" + interestRate +
                ", tenureMonths=" + tenureMonths +
                ", emiAmount=" + emiAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
