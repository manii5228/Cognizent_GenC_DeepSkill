-- ============================================================
--  SQL Setup Script for ormlearn database
--  Run these commands in MySQL before starting the application
-- ============================================================

-- Step 1: Create the schema (database)
-- > mysql -u root -p
-- mysql> create schema ormlearn;
CREATE SCHEMA IF NOT EXISTS ormlearn;

USE ormlearn;

-- Step 2: Create the country table
-- Maps to the Country entity class (@Table(name="country"))
CREATE TABLE IF NOT EXISTS country (
    co_code VARCHAR(2)  PRIMARY KEY,   -- maps to @Column(name="co_code") => code field
    co_name VARCHAR(50) NOT NULL        -- maps to @Column(name="co_name") => name field
);

-- Step 3: Insert sample data
INSERT INTO country VALUES ('IN', 'India');
INSERT INTO country VALUES ('US', 'United States of America');
