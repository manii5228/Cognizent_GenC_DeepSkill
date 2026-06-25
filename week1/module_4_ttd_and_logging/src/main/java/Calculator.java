/**
 * Calculator.java
 * A simple calculator class used to demonstrate the AAA (Arrange-Act-Assert)
 * unit testing pattern in Exercise 4.
 */
public class Calculator {

    /**
     * Adds two integers.
     * @param a first operand
     * @param b second operand
     * @return sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts b from a.
     * @param a minuend
     * @param b subtrahend
     * @return difference
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers.
     * @param a first factor
     * @param b second factor
     * @return product
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides a by b.
     * @param a dividend
     * @param b divisor (must not be zero)
     * @return quotient
     * @throws ArithmeticException if b is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
