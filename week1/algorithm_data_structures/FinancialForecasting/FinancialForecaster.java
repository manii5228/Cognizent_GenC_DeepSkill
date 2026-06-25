package FinancialForecasting;

import java.util.HashMap;
import java.util.Map;

/**
 * FinancialForecaster - Recursive algorithm to predict future values.
 *
 * ============================================================
 *  RECURSION — CONCEPT
 * ============================================================
 *  Recursion is a technique where a method calls ITSELF to solve
 *  a smaller version of the same problem.
 *
 *  Every recursive solution has two parts:
 *    1. BASE CASE  – the trivial case where no further recursion is needed.
 *    2. RECURSIVE CASE – the problem is broken down and the method calls itself.
 *
 *  Recursion simplifies problems that have a naturally self-similar structure,
 *  such as compound interest, Fibonacci sequences, tree traversal, and
 *  divide-and-conquer algorithms.
 *
 * ============================================================
 *  FINANCIAL FORECASTING FORMULA
 * ============================================================
 *  Future Value = Present Value × (1 + growthRate)^years
 *
 *  Recursive definition:
 *    futureValue(pv, r, 0) = pv                       ← base case
 *    futureValue(pv, r, n) = futureValue(pv, r, n-1)  ← recursive case
 *                            × (1 + r)
 *
 * ============================================================
 *  TIME COMPLEXITY ANALYSIS
 * ============================================================
 *  Plain Recursive approach (calculateFutureValue):
 *    T(n) = T(n-1) + O(1)  →  O(n)
 *    Each year reduces n by 1, so we make n recursive calls.
 *    Space complexity: O(n) stack frames.
 *
 *  Memoized approach (calculateFutureValueMemo):
 *    Same O(n) time complexity for the first call, but O(1) for
 *    subsequent queries on the same (pv, rate, year) triple because
 *    results are cached.  Space: O(n) for the memo table.
 *
 *  Math.pow iterative approach (calculateFutureValueIterative):
 *    O(1) time — single formula evaluation.
 *    Best for production use; shown here for comparison.
 *
 * ============================================================
 *  OPTIMISATION — MEMOIZATION
 * ============================================================
 *  A naive recursive call re-computes the same sub-problems repeatedly
 *  if called multiple times with overlapping inputs.
 *  Memoization stores already-computed results in a cache (HashMap)
 *  and returns the cached result immediately on a cache hit.
 *  This is the key idea behind Dynamic Programming.
 * ============================================================
 */
public class FinancialForecaster {

    // Memoization cache: key = "presentValue_growthRate_years"
    private final Map<String, Double> memo = new HashMap<>();

    // ------------------------------------------------------------------ //
    //  1. PLAIN RECURSIVE — O(n) time, O(n) space (call stack)
    // ------------------------------------------------------------------ //

    /**
     * Calculates the future value of an investment recursively.
     *
     * Formula: FV = PV * (1 + r)^n
     *
     * @param presentValue initial investment amount (₹ or $)
     * @param growthRate   annual growth rate as a decimal (e.g., 0.08 for 8%)
     * @param years        number of years to project
     * @return future value after 'years' years
     */
    public double calculateFutureValue(double presentValue, double growthRate, int years) {
        // Base case: no more years to compound
        if (years == 0) {
            return presentValue;
        }
        // Recursive case: compound one year at a time
        return calculateFutureValue(presentValue, growthRate, years - 1) * (1 + growthRate);
    }

    // ------------------------------------------------------------------ //
    //  2. MEMOIZED RECURSIVE — O(n) first call, O(1) cached calls
    // ------------------------------------------------------------------ //

    /**
     * Memoized version of calculateFutureValue.
     * Stores results in a HashMap to avoid redundant computation.
     *
     * @param presentValue initial investment
     * @param growthRate   annual growth rate (decimal)
     * @param years        projection horizon in years
     * @return future value
     */
    public double calculateFutureValueMemo(double presentValue, double growthRate, int years) {
        // Base case
        if (years == 0) return presentValue;

        // Build a unique cache key for this (pv, rate, years) combination
        String key = presentValue + "_" + growthRate + "_" + years;

        // Cache hit: return stored result
        if (memo.containsKey(key)) {
            System.out.println("  [Cache HIT]  year=" + years + " → returning cached result");
            return memo.get(key);
        }

        // Cache miss: compute and store
        double result = calculateFutureValueMemo(presentValue, growthRate, years - 1)
                        * (1 + growthRate);
        memo.put(key, result);
        return result;
    }

    // ------------------------------------------------------------------ //
    //  3. ITERATIVE (Math.pow) — O(1) time, O(1) space — best for production
    // ------------------------------------------------------------------ //

    /**
     * Iterative future value using the closed-form compound interest formula.
     * O(1) — no recursion, no looping.
     *
     * @param presentValue initial investment
     * @param growthRate   annual growth rate (decimal)
     * @param years        projection horizon
     * @return future value
     */
    public double calculateFutureValueIterative(double presentValue,
                                                double growthRate, int years) {
        return presentValue * Math.pow(1 + growthRate, years);
    }

    // ------------------------------------------------------------------ //
    //  4. VARIABLE GROWTH RATES — Recursive with year-by-year rates
    // ------------------------------------------------------------------ //

    /**
     * Projects future value when growth rates VARY each year.
     * This is closer to real-world financial forecasting where rates
     * change annually based on market conditions.
     *
     * @param presentValue initial investment
     * @param rates        array of per-year growth rates; rates[0] is Year 1
     * @param year         current year index (start with rates.length - 1)
     * @return future value after all years
     */
    public double calculateWithVariableRates(double presentValue,
                                             double[] rates, int year) {
        // Base case: no years remaining
        if (year < 0) return presentValue;

        // Recursive: apply this year's rate, then recurse for the next year
        return calculateWithVariableRates(presentValue, rates, year - 1) * (1 + rates[year]);
    }

    /** Clears the memoization cache. */
    public void clearCache() { memo.clear(); }
}
