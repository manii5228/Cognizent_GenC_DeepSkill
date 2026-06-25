-- ============================================================
-- exercise1_control_structures.sql
-- Exercise 1: Control Structures — Banking System
-- ============================================================
-- Scenarios covered:
--   1. Loan interest rate discount for customers above 60 years old
--   2. Promote customers with balance > $10,000 to VIP status
--   3. Loan due-date reminder for loans due within next 30 days
-- ============================================================

SET SERVEROUTPUT ON SIZE UNLIMITED;


-- ============================================================
-- SCENARIO 1:
-- The bank wants to apply a discount to loan interest rates
-- for customers above 60 years old.
--
-- PL/SQL Block:
--   - Loops through all customers
--   - Calculates customer age from DOB
--   - If age > 60, applies a 1% discount to LoanInterestRate
--   - Ensures rate does not go below 0%
-- ============================================================

DECLARE
    -- Cursor fetches all customers who have a loan interest rate > 0
    CURSOR c_customers IS
        SELECT CustomerID, Name, DOB, LoanInterestRate
        FROM   Customers
        WHERE  LoanInterestRate > 0
        FOR UPDATE OF LoanInterestRate;

    v_age           NUMBER;
    v_new_rate      NUMBER;
    v_discount      CONSTANT NUMBER := 1.0;   -- 1% discount
    v_updated_count NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SCENARIO 1: Senior Citizen Loan Interest Rate Discount');
    DBMS_OUTPUT.PUT_LINE('  Discount Applied: 1% for customers above 60 years old');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    FOR cust IN c_customers LOOP

        -- Calculate age in years
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, cust.DOB) / 12);

        IF v_age > 60 THEN
            -- Apply discount; ensure rate doesn't drop below 0
            v_new_rate := GREATEST(cust.LoanInterestRate - v_discount, 0);

            UPDATE Customers
            SET    LoanInterestRate = v_new_rate
            WHERE  CURRENT OF c_customers;

            v_updated_count := v_updated_count + 1;

            DBMS_OUTPUT.PUT_LINE(
                '  [DISCOUNT APPLIED]' ||
                '  CustomerID: ' || cust.CustomerID ||
                '  | Name: '    || RPAD(cust.Name, 18) ||
                '  | Age: '     || v_age ||
                '  | Old Rate: '|| cust.LoanInterestRate || '%' ||
                '  | New Rate: '|| v_new_rate || '%'
            );
        ELSE
            DBMS_OUTPUT.PUT_LINE(
                '  [NO CHANGE]      ' ||
                '  CustomerID: ' || cust.CustomerID ||
                '  | Name: '    || RPAD(cust.Name, 18) ||
                '  | Age: '     || v_age ||
                '  | Rate: '    || cust.LoanInterestRate || '% (age ≤ 60)'
            );
        END IF;

    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('  Customers Eligible & Updated: ' || v_updated_count);
    DBMS_OUTPUT.PUT_LINE('  Scenario 1 — Completed.');
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('[ERROR] Scenario 1 failed: ' || SQLERRM);
        RAISE;
END;
/


-- ============================================================
-- SCENARIO 2:
-- A customer can be promoted to VIP status based on their balance.
--
-- PL/SQL Block:
--   - Iterates through all customers
--   - If Balance > $10,000, sets IsVIP = 'TRUE'
--   - If Balance ≤ $10,000 and IsVIP was 'TRUE', demotes to 'FALSE'
--   - Reports each status change
-- ============================================================

DECLARE
    -- Cursor over all customers
    CURSOR c_customers IS
        SELECT CustomerID, Name, Balance, IsVIP
        FROM   Customers
        FOR UPDATE OF IsVIP;

    v_vip_threshold CONSTANT NUMBER := 10000;
    v_promoted_count NUMBER := 0;
    v_demoted_count  NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SCENARIO 2: VIP Status Promotion');
    DBMS_OUTPUT.PUT_LINE('  VIP Threshold: Balance > $10,000');
    DBMS_OUTPUT.PUT_LINE('============================================================');

    FOR cust IN c_customers LOOP

        IF cust.Balance > v_vip_threshold THEN

            -- Promote to VIP if not already VIP
            IF cust.IsVIP = 'FALSE' THEN
                UPDATE Customers
                SET    IsVIP = 'TRUE'
                WHERE  CURRENT OF c_customers;

                v_promoted_count := v_promoted_count + 1;

                DBMS_OUTPUT.PUT_LINE(
                    '  [PROMOTED → VIP]    ' ||
                    '  CustomerID: ' || cust.CustomerID ||
                    '  | Name: '    || RPAD(cust.Name, 18) ||
                    '  | Balance: $'|| TO_CHAR(cust.Balance, 'FM99,999,990.00')
                );
            ELSE
                DBMS_OUTPUT.PUT_LINE(
                    '  [ALREADY VIP]       ' ||
                    '  CustomerID: ' || cust.CustomerID ||
                    '  | Name: '    || RPAD(cust.Name, 18) ||
                    '  | Balance: $'|| TO_CHAR(cust.Balance, 'FM99,999,990.00')
                );
            END IF;

        ELSE

            -- Demote if previously VIP (balance fell below threshold)
            IF cust.IsVIP = 'TRUE' THEN
                UPDATE Customers
                SET    IsVIP = 'FALSE'
                WHERE  CURRENT OF c_customers;

                v_demoted_count := v_demoted_count + 1;

                DBMS_OUTPUT.PUT_LINE(
                    '  [DEMOTED ← VIP]     ' ||
                    '  CustomerID: ' || cust.CustomerID ||
                    '  | Name: '    || RPAD(cust.Name, 18) ||
                    '  | Balance: $'|| TO_CHAR(cust.Balance, 'FM99,999,990.00')
                );
            ELSE
                DBMS_OUTPUT.PUT_LINE(
                    '  [NOT ELIGIBLE]      ' ||
                    '  CustomerID: ' || cust.CustomerID ||
                    '  | Name: '    || RPAD(cust.Name, 18) ||
                    '  | Balance: $'|| TO_CHAR(cust.Balance, 'FM99,999,990.00')
                );
            END IF;

        END IF;

    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('  Customers Promoted to VIP : ' || v_promoted_count);
    DBMS_OUTPUT.PUT_LINE('  Customers Demoted from VIP: ' || v_demoted_count);
    DBMS_OUTPUT.PUT_LINE('  Scenario 2 — Completed.');
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('[ERROR] Scenario 2 failed: ' || SQLERRM);
        RAISE;
END;
/


-- ============================================================
-- SCENARIO 3:
-- The bank wants to send reminders to customers whose loans
-- are due within the next 30 days.
--
-- PL/SQL Block:
--   - Fetches all Active loans with DueDate BETWEEN today and today+30
--   - Also flags overdue loans (DueDate < SYSDATE) with a warning
--   - Prints a reminder message for each affected customer
--   - Uses a FOR loop with a cursor that joins Loans and Customers
-- ============================================================

DECLARE
    -- Cursor: loans due within next 30 days OR already overdue
    CURSOR c_due_loans IS
        SELECT l.LoanID,
               l.LoanAmount,
               l.DueDate,
               c.CustomerID,
               c.Name         AS CustomerName,
               TRUNC(l.DueDate - SYSDATE) AS DaysRemaining
        FROM   Loans     l
        JOIN   Customers c ON l.CustomerID = c.CustomerID
        WHERE  l.Status  = 'Active'
          AND  l.DueDate <= SYSDATE + 30
        ORDER  BY l.DueDate ASC;

    v_reminder_count NUMBER := 0;
    v_overdue_count  NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('  SCENARIO 3: Loan Due-Date Reminder System');
    DBMS_OUTPUT.PUT_LINE('  Criteria   : Active loans due within next 30 days');
    DBMS_OUTPUT.PUT_LINE('  As of Date : ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('============================================================');

    FOR loan IN c_due_loans LOOP

        v_reminder_count := v_reminder_count + 1;

        IF loan.DaysRemaining < 0 THEN
            -- Overdue loan
            v_overdue_count := v_overdue_count + 1;
            DBMS_OUTPUT.PUT_LINE(
                '  *** OVERDUE REMINDER ***' || CHR(10) ||
                '      Dear ' || loan.CustomerName || ' (CustomerID: ' || loan.CustomerID || '),' || CHR(10) ||
                '      Your Loan #' || loan.LoanID ||
                '  of $' || TO_CHAR(loan.LoanAmount, 'FM99,999,990.00') ||
                '  was due on ' || TO_CHAR(loan.DueDate, 'DD-MON-YYYY') ||
                '  (' || ABS(loan.DaysRemaining) || ' days ago).' || CHR(10) ||
                '      Please clear your dues immediately to avoid penalties.' || CHR(10)
            );
        ELSE
            -- Upcoming due loan
            DBMS_OUTPUT.PUT_LINE(
                '  >>> PAYMENT REMINDER <<<' || CHR(10) ||
                '      Dear ' || loan.CustomerName || ' (CustomerID: ' || loan.CustomerID || '),' || CHR(10) ||
                '      Your Loan #' || loan.LoanID ||
                '  of $' || TO_CHAR(loan.LoanAmount, 'FM99,999,990.00') ||
                '  is due on ' || TO_CHAR(loan.DueDate, 'DD-MON-YYYY') ||
                '  (' || loan.DaysRemaining || ' day(s) remaining).' || CHR(10) ||
                '      Please ensure sufficient funds are available.' || CHR(10)
            );
        END IF;

    END LOOP;

    -- Summary
    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
    IF v_reminder_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('  No loans due in the next 30 days. All accounts are clear.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('  Total Reminders Sent : ' || v_reminder_count);
        DBMS_OUTPUT.PUT_LINE('  Overdue Loans        : ' || v_overdue_count);
        DBMS_OUTPUT.PUT_LINE('  Upcoming (≤30 days)  : ' || (v_reminder_count - v_overdue_count));
    END IF;
    DBMS_OUTPUT.PUT_LINE('  Scenario 3 — Completed.');
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('[ERROR] Scenario 3 failed: ' || SQLERRM);
        RAISE;
END;
/
