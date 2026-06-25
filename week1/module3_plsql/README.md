# Module 3 — PL/SQL: Stored Procedures & Control Structures

## Folder Structure
```
module3_plsql/
├── schema_setup.sql                 ← Run FIRST — creates tables & inserts sample data
├── exercise3_stored_procedures.sql  ← Exercise 3: Stored Procedures (3 scenarios)
└── exercise1_control_structures.sql ← Exercise 1: Control Structures (3 scenarios)
```

## How to Run (Oracle SQL*Plus or SQL Developer)

```sql
-- Step 1: Setup schema
@schema_setup.sql

-- Step 2: Run stored procedures
@exercise3_stored_procedures.sql

-- Step 3: Run control structure blocks
@exercise1_control_structures.sql
```

---

## Exercise 3: Stored Procedures

| Scenario | Procedure | Description |
|---|---|---|
| 1 | `ProcessMonthlyInterest` | Applies 1% interest to all Savings account balances |
| 2 | `UpdateEmployeeBonus(dept, pct)` | Adds bonus % to salaries of all employees in a department |
| 3 | `TransferFunds(from, to, amount)` | Transfers funds between accounts with balance validation |

### Key Concepts Used
- `CREATE OR REPLACE PROCEDURE`
- Cursor with `FOR UPDATE` (row-level locking)
- `RAISE_APPLICATION_ERROR` for custom exceptions
- `COMMIT` / `ROLLBACK` for transaction control
- `DBMS_OUTPUT.PUT_LINE` for logging

---

## Exercise 1: Control Structures

| Scenario | Description |
|---|---|
| 1 | Loop through all customers; apply 1% loan rate discount for age > 60 |
| 2 | Iterate all customers; set `IsVIP = TRUE` if balance > $10,000 |
| 3 | Fetch loans due within 30 days; print reminder/overdue messages |

### Key Concepts Used
- Anonymous PL/SQL blocks (`DECLARE ... BEGIN ... EXCEPTION ... END`)
- `FOR` loop with explicit cursor
- `IF / ELSIF / ELSE` conditional logic
- `MONTHS_BETWEEN` for age calculation
- `CURSOR` with `JOIN` across multiple tables
- `TRUNC`, `GREATEST`, `TO_CHAR` built-in functions
