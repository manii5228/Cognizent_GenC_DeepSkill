-- ============================================================
-- schema_setup.sql
-- Banking System - Table Definitions & Sample Data
-- Run this script FIRST before executing any exercises.
-- Compatible with: Oracle Database (PL/SQL)
-- ============================================================

-- ----------------------------------------------------------------
-- DROP tables if they exist (clean slate for re-runs)
-- ----------------------------------------------------------------
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE LoanReminders';   EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Loans';            EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Accounts';         EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Employees';        EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Customers';        EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- ----------------------------------------------------------------
-- TABLE: Customers
-- ----------------------------------------------------------------
CREATE TABLE Customers (
    CustomerID   NUMBER(10)     PRIMARY KEY,
    Name         VARCHAR2(100)  NOT NULL,
    DOB          DATE           NOT NULL,
    Balance      NUMBER(15, 2)  DEFAULT 0.00,
    IsVIP        VARCHAR2(5)    DEFAULT 'FALSE'
                                CHECK (IsVIP IN ('TRUE', 'FALSE')),
    LoanInterestRate NUMBER(5,2) DEFAULT 0.00   -- current loan interest rate (%)
);

-- ----------------------------------------------------------------
-- TABLE: Accounts
-- ----------------------------------------------------------------
CREATE TABLE Accounts (
    AccountID    NUMBER(10)     PRIMARY KEY,
    CustomerID   NUMBER(10)     REFERENCES Customers(CustomerID),
    AccountType  VARCHAR2(20)   NOT NULL
                                CHECK (AccountType IN ('Savings','Checking','Fixed')),
    Balance      NUMBER(15, 2)  DEFAULT 0.00
);

-- ----------------------------------------------------------------
-- TABLE: Employees
-- ----------------------------------------------------------------
CREATE TABLE Employees (
    EmployeeID   NUMBER(10)     PRIMARY KEY,
    Name         VARCHAR2(100)  NOT NULL,
    Department   VARCHAR2(50)   NOT NULL,
    Salary       NUMBER(12, 2)  NOT NULL
);

-- ----------------------------------------------------------------
-- TABLE: Loans
-- ----------------------------------------------------------------
CREATE TABLE Loans (
    LoanID       NUMBER(10)     PRIMARY KEY,
    CustomerID   NUMBER(10)     REFERENCES Customers(CustomerID),
    LoanAmount   NUMBER(15, 2)  NOT NULL,
    DueDate      DATE           NOT NULL,
    Status       VARCHAR2(20)   DEFAULT 'Active'
                                CHECK (Status IN ('Active','Closed','Defaulted'))
);

-- ----------------------------------------------------------------
-- SAMPLE DATA: Customers
-- ----------------------------------------------------------------
INSERT INTO Customers VALUES (1001, 'Alice Johnson',   DATE '1958-03-15', 15000.00, 'FALSE', 8.50);
INSERT INTO Customers VALUES (1002, 'Bob Martinez',    DATE '1990-07-22', 9500.00,  'FALSE', 10.00);
INSERT INTO Customers VALUES (1003, 'Carol Williams',  DATE '1955-11-30', 22000.00, 'FALSE', 9.00);
INSERT INTO Customers VALUES (1004, 'David Brown',     DATE '1985-05-10', 4500.00,  'FALSE', 12.00);
INSERT INTO Customers VALUES (1005, 'Eva Davis',       DATE '1948-09-08', 31000.00, 'FALSE', 7.50);
INSERT INTO Customers VALUES (1006, 'Frank Wilson',    DATE '1995-01-25', 800.00,   'FALSE', 0.00);
INSERT INTO Customers VALUES (1007, 'Grace Lee',       DATE '1952-06-14', 18000.00, 'FALSE', 8.00);

-- ----------------------------------------------------------------
-- SAMPLE DATA: Accounts
-- ----------------------------------------------------------------
INSERT INTO Accounts VALUES (2001, 1001, 'Savings',  15000.00);
INSERT INTO Accounts VALUES (2002, 1002, 'Savings',   9500.00);
INSERT INTO Accounts VALUES (2003, 1003, 'Checking', 22000.00);
INSERT INTO Accounts VALUES (2004, 1003, 'Savings',   5000.00);
INSERT INTO Accounts VALUES (2005, 1004, 'Savings',   4500.00);
INSERT INTO Accounts VALUES (2006, 1005, 'Savings',  31000.00);
INSERT INTO Accounts VALUES (2007, 1006, 'Checking',   800.00);
INSERT INTO Accounts VALUES (2008, 1007, 'Savings',  18000.00);

-- ----------------------------------------------------------------
-- SAMPLE DATA: Employees
-- ----------------------------------------------------------------
INSERT INTO Employees VALUES (3001, 'Henry Clark',    'IT',       75000.00);
INSERT INTO Employees VALUES (3002, 'Irene Scott',    'IT',       82000.00);
INSERT INTO Employees VALUES (3003, 'Jack Adams',     'HR',       58000.00);
INSERT INTO Employees VALUES (3004, 'Karen White',    'HR',       62000.00);
INSERT INTO Employees VALUES (3005, 'Liam Harris',    'Finance',  95000.00);
INSERT INTO Employees VALUES (3006, 'Mia Thompson',   'Finance',  88000.00);
INSERT INTO Employees VALUES (3007, 'Noah Walker',    'Operations',70000.00);

-- ----------------------------------------------------------------
-- SAMPLE DATA: Loans
-- ----------------------------------------------------------------
INSERT INTO Loans VALUES (4001, 1001, 50000.00,  SYSDATE + 20,  'Active');   -- due in 20 days
INSERT INTO Loans VALUES (4002, 1002, 30000.00,  SYSDATE + 10,  'Active');   -- due in 10 days
INSERT INTO Loans VALUES (4003, 1003, 75000.00,  SYSDATE + 45,  'Active');   -- due in 45 days (excluded)
INSERT INTO Loans VALUES (4004, 1004, 20000.00,  SYSDATE + 5,   'Active');   -- due in 5 days
INSERT INTO Loans VALUES (4005, 1005, 100000.00, SYSDATE + 90,  'Active');   -- due in 90 days (excluded)
INSERT INTO Loans VALUES (4006, 1006, 15000.00,  SYSDATE + 28,  'Active');   -- due in 28 days
INSERT INTO Loans VALUES (4007, 1007, 40000.00,  SYSDATE - 10,  'Active');   -- overdue

COMMIT;

PROMPT ============================================================
PROMPT   Schema setup complete. Tables created with sample data.
PROMPT ============================================================
