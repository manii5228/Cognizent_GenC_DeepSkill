package FinancialForecasting;

/**
 * FinancialForecastingTest - Test class for the Financial Forecasting tool.
 *
 * Tests cover:
 *   1. Plain recursive future value calculation
 *   2. Memoized recursive calculation (with cache-hit demonstration)
 *   3. Iterative (O(1)) calculation for comparison
 *   4. Multi-year projection table
 *   5. Variable annual growth rates
 *   6. Performance analysis of recursive vs iterative
 */
public class FinancialForecastingTest {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("      Financial Forecasting Tool — Recursive Analysis");
        System.out.println("============================================================\n");

        FinancialForecaster forecaster = new FinancialForecaster();

        // ---------------------------------------------------------------- //
        //  SECTION 1 — Plain Recursive Future Value
        // ---------------------------------------------------------------- //
        System.out.println("============================================================");
        System.out.println("  SECTION 1 : Plain Recursive — O(n)");
        System.out.println("============================================================");

        double presentValue = 100_000.0;   // ₹1,00,000 initial investment
        double growthRate   = 0.08;        // 8% annual growth
        int    years        = 10;

        double fv = forecaster.calculateFutureValue(presentValue, growthRate, years);
        System.out.printf("  Principal   : ₹%,.2f%n", presentValue);
        System.out.printf("  Growth Rate : %.1f%% per annum%n", growthRate * 100);
        System.out.printf("  Tenure      : %d years%n", years);
        System.out.printf("  Future Value: ₹%,.2f%n%n", fv);

        // ---------------------------------------------------------------- //
        //  SECTION 2 — Memoized Recursive (with cache demonstration)
        // ---------------------------------------------------------------- //
        System.out.println("============================================================");
        System.out.println("  SECTION 2 : Memoized Recursive — Cache Demonstration");
        System.out.println("============================================================");

        System.out.println("\n  [First call — cache is empty, all results computed fresh]");
        double fvMemo1 = forecaster.calculateFutureValueMemo(presentValue, growthRate, 5);
        System.out.printf("  FV after 5 years : ₹%,.2f%n", fvMemo1);

        System.out.println("\n  [Second call — overlapping sub-problem (year 5 already cached)]");
        double fvMemo2 = forecaster.calculateFutureValueMemo(presentValue, growthRate, 7);
        System.out.printf("  FV after 7 years : ₹%,.2f%n", fvMemo2);

        // ---------------------------------------------------------------- //
        //  SECTION 3 — Iterative Comparison
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 3 : Iterative vs Recursive Comparison");
        System.out.println("============================================================");

        System.out.println("\n  Both methods for ₹1,00,000 @ 8% for 10 years:");
        double recursiveResult  = forecaster.calculateFutureValue(presentValue, growthRate, years);
        double iterativeResult  = forecaster.calculateFutureValueIterative(presentValue, growthRate, years);
        System.out.printf("  Recursive Result  : ₹%,.2f%n", recursiveResult);
        System.out.printf("  Iterative Result  : ₹%,.2f%n", iterativeResult);
        System.out.printf("  Match?            : %s%n%n",
                Math.abs(recursiveResult - iterativeResult) < 0.001 ? "✔ YES" : "✘ NO");

        // ---------------------------------------------------------------- //
        //  SECTION 4 — Multi-Year Projection Table
        // ---------------------------------------------------------------- //
        System.out.println("============================================================");
        System.out.println("  SECTION 4 : Multi-Year Investment Projection Table");
        System.out.println("============================================================");

        System.out.println("\n  Principal: ₹1,00,000  |  Growth Rate: 8% p.a.");
        System.out.println("  ┌────────┬─────────────────┬─────────────────┐");
        System.out.println("  │  Year  │   Future Value  │   Growth (%)    │");
        System.out.println("  ├────────┼─────────────────┼─────────────────┤");

        forecaster.clearCache();
        for (int y = 1; y <= 20; y++) {
            double fvYear = forecaster.calculateFutureValueMemo(presentValue, growthRate, y);
            double growth = ((fvYear - presentValue) / presentValue) * 100;
            System.out.printf("  │  %4d  │  ₹%,12.2f  │     %7.2f%%    │%n", y, fvYear, growth);
        }
        System.out.println("  └────────┴─────────────────┴─────────────────┘");

        // ---------------------------------------------------------------- //
        //  SECTION 5 — Variable Annual Growth Rates
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 5 : Variable Annual Growth Rates");
        System.out.println("============================================================");

        double[] rates = {0.06, 0.09, 0.07, 0.12, 0.05};  // year-wise rates
        System.out.println("\n  Year-wise growth rates:");
        for (int i = 0; i < rates.length; i++) {
            System.out.printf("    Year %d → %.0f%%%n", i + 1, rates[i] * 100);
        }

        double fvVariable = forecaster.calculateWithVariableRates(
                presentValue, rates, rates.length - 1);
        System.out.printf("%n  Principal     : ₹%,.2f%n", presentValue);
        System.out.printf("  Future Value  : ₹%,.2f%n", fvVariable);

        // ---------------------------------------------------------------- //
        //  SECTION 6 — Performance Analysis
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 6 : Performance Analysis");
        System.out.println("============================================================");

        int largeN = 1000;

        long t1 = System.nanoTime();
        forecaster.calculateFutureValue(presentValue, growthRate, largeN);
        long recursiveTime = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        forecaster.calculateFutureValueIterative(presentValue, growthRate, largeN);
        long iterativeTime = System.nanoTime() - t2;

        System.out.printf("%n  Projection horizon : %d years%n", largeN);
        System.out.printf("  Recursive  O(n)    : %,d ns%n", recursiveTime);
        System.out.printf("  Iterative  O(1)    : %,d ns%n", iterativeTime);

        // ---------------------------------------------------------------- //
        //  SECTION 7 — Complexity Summary
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 7 : Time & Space Complexity Summary");
        System.out.println("============================================================");
        System.out.println("""
                Approach              | Time     | Space  | Notes
                ----------------------|----------|--------|---------------------------
                Plain Recursive       | O(n)     | O(n)   | Stack overflow risk for large n
                Memoized Recursive    | O(n)*    | O(n)   | *O(1) for cached sub-problems
                Iterative (Math.pow)  | O(1)     | O(1)   | Best for production use

                Optimisation Strategies:
                ─────────────────────────────────────────────────────────────
                1. MEMOIZATION (Top-Down DP): Cache computed results in a
                   HashMap to avoid re-computation of overlapping sub-problems.

                2. ITERATIVE FORMULA: Replace recursion entirely with the
                   closed-form compound interest formula — O(1) time & space,
                   no stack overflow risk.

                3. TAIL RECURSION: Refactor the recursion so the recursive
                   call is the LAST operation. Some JVMs can optimise this
                   into iteration (Tail-Call Optimisation).

                4. MATRIX EXPONENTIATION: For more complex recurrence relations
                   (e.g., Fibonacci), matrix exponentiation achieves O(log n).
                """);
    }
}
