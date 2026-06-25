-- ============================================================
-- exercise3_stored_procedures.sql
-- Exercise 3: Stored Procedures — Banking System
-- ============================================================
-- Scenarios covered:
--   1. ProcessMonthlyInterest  – apply 1% interest to all Savings accounts
--   2. UpdateEmployeeBonus     – add a bonus % to salaries in a department
--   3. TransferFunds           – transfer amount between accounts with balance check
-- ============================================================

-- ============================================================
-- SCENARIO 1:
-- The bank needs to process monthly interest for all savings
-- accounts by applying an interest rate of 1% to the balance.
--
-- Procedure: ProcessMonthlyInterest
--   - Loops through every Savings account
--   - Applies 1% interest: new_balance = balance * 1.01
--   - Updates the Accounts table
--   - Logs each update with DBMS_OUTPUT
-- ============================================================

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS

    -- Cursor to fetch all Savings accounts
    CURSOR c_savings IS
        SELECT AccountID, Balance
        FROM   Accounts
        WHERE  AccountType = 'Savings'
        FOR UPDATE OF Balance;             -- row-level lock for safe update

    v_new_balance   Accounts.Balance%TYPE;
    v_count         NUMBER := 0;
    v_interest_rate CONSTANT NUMBER := 0.01;   -- 1% monthly interest

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  ProcessMonthlyInterest — Starting...');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    FOR rec IN c_savings LOOP
        v_new_balance := rec.Balance + (rec.Balance * v_interest_rate);

        -- Update balance in Accounts table
        UPDATE Accounts
        SET    Balance = v_new_balance
        WHERE  CURRENT OF c_savings;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE(
            '  AccountID: ' || rec.AccountID ||
            '  | Old Balance: $' || TO_CHAR(rec.Balance,     'FM99,999,990.00') ||
            '  | Interest: $'    || TO_CHAR(rec.Balance * v_interest_rate, 'FM9,990.00') ||
            '  | New Balance: $' || TO_CHAR(v_new_balance,   'FM99,999,990.00')
        );
    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('  Total Savings Accounts Updated: ' || v_count);
    DBMS_OUTPUT.PUT_LINE('  ProcessMonthlyInterest — Completed successfully.');
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('[ERROR] ProcessMonthlyInterest failed: ' || SQLERRM);
        RAISE;
END ProcessMonthlyInterest;
/


-- ============================================================
-- SCENARIO 2:
-- The bank wants to implement a bonus scheme for employees
-- based on their performance, by department.
--
-- Procedure: UpdateEmployeeBonus
--   Parameters:
--     p_department  VARCHAR2  – name of the department
--     p_bonus_pct   NUMBER    – bonus percentage (e.g., 10 for 10%)
--
--   - Finds all employees in the given department
--   - Adds bonus_pct% to each employee's salary
--   - Raises an exception if department is not found
-- ============================================================

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department  IN VARCHAR2,
    p_bonus_pct   IN NUMBER
) AS

    v_count         NUMBER := 0;
    v_bonus_amount  NUMBER;
    v_new_salary    NUMBER;

    -- Cursor for employees in the given department
    CURSOR c_emp IS
        SELECT EmployeeID, Name, Salary
        FROM   Employees
        WHERE  UPPER(Department) = UPPER(p_department)
        FOR UPDATE OF Salary;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  UpdateEmployeeBonus');
    DBMS_OUTPUT.PUT_LINE('  Department : ' || p_department);
    DBMS_OUTPUT.PUT_LINE('  Bonus      : ' || p_bonus_pct || '%');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    -- Validate bonus percentage
    IF p_bonus_pct <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001,
            'Bonus percentage must be greater than zero. Received: ' || p_bonus_pct);
    END IF;

    FOR emp IN c_emp LOOP
        v_bonus_amount := emp.Salary * (p_bonus_pct / 100);
        v_new_salary   := emp.Salary + v_bonus_amount;

        UPDATE Employees
        SET    Salary = v_new_salary
        WHERE  CURRENT OF c_emp;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE(
            '  EmpID: ' || emp.EmployeeID ||
            '  | Name: '        || RPAD(emp.Name, 15) ||
            '  | Old Salary: $' || TO_CHAR(emp.Salary,      'FM99,990.00') ||
            '  | Bonus: $'      || TO_CHAR(v_bonus_amount,  'FM9,990.00')  ||
            '  | New Salary: $' || TO_CHAR(v_new_salary,    'FM99,990.00')
        );
    END LOOP;

    -- Raise error if no employees were found in the department
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20002,
            'No employees found in department: ' || p_department);
    END IF;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('  Employees Updated: ' || v_count);
    DBMS_OUTPUT.PUT_LINE('  UpdateEmployeeBonus — Completed successfully.');
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('[ERROR] UpdateEmployeeBonus failed: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/


-- ============================================================
-- SCENARIO 3:
-- Customers should be able to transfer funds between accounts.
--
-- Procedure: TransferFunds
--   Parameters:
--     p_from_account  NUMBER  – source AccountID
--     p_to_account    NUMBER  – destination AccountID
--     p_amount        NUMBER  – amount to transfer
--
--   - Validates that source account exists
--   - Validates that destination account exists
--   - Checks that source has sufficient balance
--   - Deducts from source and credits to destination atomically
--   - Rolls back the entire transaction on any error
-- ============================================================

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account  IN NUMBER,
    p_to_account    IN NUMBER,
    p_amount        IN NUMBER
) AS

    v_from_balance  Accounts.Balance%TYPE;
    v_to_balance    Accounts.Balance%TYPE;

    -- Custom exception for insufficient funds
    e_insufficient_funds EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_insufficient_funds, -20003);

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  TransferFunds');
    DBMS_OUTPUT.PUT_LINE('  From Account : ' || p_from_account);
    DBMS_OUTPUT.PUT_LINE('  To Account   : ' || p_to_account);
    DBMS_OUTPUT.PUT_LINE('  Amount       : $' || TO_CHAR(p_amount, 'FM99,999,990.00'));
    DBMS_OUTPUT.PUT_LINE('============================================================');

    -- Validate transfer amount
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20004,
            'Transfer amount must be positive. Received: ' || p_amount);
    END IF;

    -- Validate source account exists and lock the row
    BEGIN
        SELECT Balance INTO v_from_balance
        FROM   Accounts
        WHERE  AccountID = p_from_account
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20005,
                'Source account not found: ' || p_from_account);
    END;

    -- Validate destination account exists and lock the row
    BEGIN
        SELECT Balance INTO v_to_balance
        FROM   Accounts
        WHERE  AccountID = p_to_account
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20006,
                'Destination account not found: ' || p_to_account);
    END;

    -- Check sufficient balance in source account
    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20003,
            'Insufficient balance in account ' || p_from_account ||
            '. Available: $' || TO_CHAR(v_from_balance, 'FM99,999,990.00') ||
            ', Required: $'  || TO_CHAR(p_amount,       'FM99,999,990.00'));
    END IF;

    -- Deduct from source account
    UPDATE Accounts
    SET    Balance = Balance - p_amount
    WHERE  AccountID = p_from_account;

    -- Credit to destination account
    UPDATE Accounts
    SET    Balance = Balance + p_amount
    WHERE  AccountID = p_to_account;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('  [SUCCESS] Transfer completed.');
    DBMS_OUTPUT.PUT_LINE('  Source Balance   : $' || TO_CHAR(v_from_balance,            'FM99,999,990.00') ||
                         '  → $' || TO_CHAR(v_from_balance - p_amount, 'FM99,999,990.00'));
    DBMS_OUTPUT.PUT_LINE('  Dest Balance     : $' || TO_CHAR(v_to_balance,              'FM99,999,990.00') ||
                         '  → $' || TO_CHAR(v_to_balance   + p_amount, 'FM99,999,990.00'));
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN e_insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('[ERROR] ' || SQLERRM);
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('[ERROR] TransferFunds failed: ' || SQLERRM);
        RAISE;
END TransferFunds;
/


-- ============================================================
-- TEST BLOCK — Execute all three stored procedures
-- ============================================================

SET SERVEROUTPUT ON SIZE UNLIMITED;

PROMPT
PROMPT ============================================================
PROMPT   TESTING: ProcessMonthlyInterest
PROMPT ============================================================
BEGIN
    ProcessMonthlyInterest;
END;
/

PROMPT
PROMPT ============================================================
PROMPT   TESTING: UpdateEmployeeBonus — IT dept, 15% bonus
PROMPT ============================================================
BEGIN
    UpdateEmployeeBonus('IT', 15);
END;
/

PROMPT
PROMPT ============================================================
PROMPT   TESTING: UpdateEmployeeBonus — Finance dept, 10% bonus
PROMPT ============================================================
BEGIN
    UpdateEmployeeBonus('Finance', 10);
END;
/

PROMPT
PROMPT ============================================================
PROMPT   TESTING: TransferFunds — Account 2001 to 2007 ($2000)
PROMPT ============================================================
BEGIN
    TransferFunds(2001, 2007, 2000);
END;
/

PROMPT
PROMPT ============================================================
PROMPT   TESTING: TransferFunds — Insufficient Balance (should fail)
PROMPT ============================================================
BEGIN
    TransferFunds(2007, 2001, 999999);   -- account 2007 has ~$2800
END;
/

PROMPT
PROMPT ============================================================
PROMPT   TESTING: UpdateEmployeeBonus — Invalid dept (should fail)
PROMPT ============================================================
BEGIN
    UpdateEmployeeBonus('Marketing', 20);
END;
/
