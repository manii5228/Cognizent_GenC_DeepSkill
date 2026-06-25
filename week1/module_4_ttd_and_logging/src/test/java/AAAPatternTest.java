import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AAAPatternTest.java — Exercise 4: Arrange-Act-Assert (AAA) Pattern,
 *                        Test Fixtures, @Before and @After
 *
 * The AAA Pattern structures every test into three clear phases:
 *
 *   ARRANGE  — Set up the preconditions and inputs (create objects, set values).
 *   ACT      — Execute the code under test (call the method being tested).
 *   ASSERT   — Verify the outcome matches the expected result.
 *
 * @Before  — Runs BEFORE each individual @Test method (setup).
 * @After   — Runs AFTER  each individual @Test method (teardown).
 *
 * These annotations ensure each test starts with a fresh, consistent
 * state and that resources are released after each test.
 */
public class AAAPatternTest {

    // Test fixture — shared object initialised before each test
    private Calculator calculator;

    // ------------------------------------------------------------------ //
    //  Setup — @Before runs before EVERY @Test method
    // ------------------------------------------------------------------ //

    /**
     * Sets up the test fixture.
     * Called before each test so every test gets a fresh Calculator instance.
     */
    @Before
    public void setUp() {
        calculator = new Calculator();
        System.out.println("\n[Setup]    @Before — Calculator instance created.");
    }

    // ------------------------------------------------------------------ //
    //  Teardown — @After runs after EVERY @Test method
    // ------------------------------------------------------------------ //

    /**
     * Tears down the test fixture.
     * Called after each test to release resources or reset state.
     */
    @After
    public void tearDown() {
        calculator = null;
        System.out.println("[Teardown] @After  — Calculator instance set to null.\n");
    }

    // ------------------------------------------------------------------ //
    //  Test Cases — each follows the Arrange → Act → Assert structure
    // ------------------------------------------------------------------ //

    @Test
    public void testAddition() {
        System.out.println("[Test] testAddition running...");

        // ARRANGE — define inputs
        int a = 10;
        int b = 5;

        // ACT — call the method under test
        int result = calculator.add(a, b);

        // ASSERT — verify the result
        assertEquals("10 + 5 should equal 15", 15, result);
        System.out.println("[Test] testAddition PASSED. Result: " + result);
    }

    @Test
    public void testSubtraction() {
        System.out.println("[Test] testSubtraction running...");

        // ARRANGE
        int a = 20;
        int b = 8;

        // ACT
        int result = calculator.subtract(a, b);

        // ASSERT
        assertEquals("20 - 8 should equal 12", 12, result);
        System.out.println("[Test] testSubtraction PASSED. Result: " + result);
    }

    @Test
    public void testMultiplication() {
        System.out.println("[Test] testMultiplication running...");

        // ARRANGE
        int a = 6;
        int b = 7;

        // ACT
        int result = calculator.multiply(a, b);

        // ASSERT
        assertEquals("6 × 7 should equal 42", 42, result);
        System.out.println("[Test] testMultiplication PASSED. Result: " + result);
    }

    @Test
    public void testDivision() {
        System.out.println("[Test] testDivision running...");

        // ARRANGE
        double a = 15.0;
        double b = 4.0;

        // ACT
        double result = calculator.divide(a, b);

        // ASSERT — use delta for floating-point comparison
        assertEquals("15.0 / 4.0 should equal 3.75", 3.75, result, 0.0001);
        System.out.println("[Test] testDivision PASSED. Result: " + result);
    }

    @Test
    public void testDivisionByZeroThrowsException() {
        System.out.println("[Test] testDivisionByZeroThrowsException running...");

        // ARRANGE
        double a = 10.0;
        double b = 0.0;

        // ACT + ASSERT — verify that ArithmeticException is thrown
        try {
            calculator.divide(a, b);
            fail("Expected ArithmeticException was not thrown");  // fails if no exception
        } catch (ArithmeticException e) {
            assertEquals("Division by zero is not allowed.", e.getMessage());
            System.out.println("[Test] testDivisionByZeroThrowsException PASSED. " +
                               "Exception message: " + e.getMessage());
        }
    }

    @Test
    public void testAdditionWithNegativeNumbers() {
        System.out.println("[Test] testAdditionWithNegativeNumbers running...");

        // ARRANGE
        int a = -5;
        int b = -3;

        // ACT
        int result = calculator.add(a, b);

        // ASSERT
        assertEquals("(-5) + (-3) should equal -8", -8, result);
        assertTrue("Result should be negative", result < 0);
        System.out.println("[Test] testAdditionWithNegativeNumbers PASSED. Result: " + result);
    }
}
