package EcommerceSearch;

import java.util.Arrays;

/**
 * EcommerceSearchTest - Test class for the e-commerce search functionality.
 *
 * Demonstrates and analyses:
 *   1. Linear Search  – O(n) on an unsorted array
 *   2. Binary Search  – O(log n) on a sorted array
 *   3. Performance comparison with timing measurements
 */
public class EcommerceSearchTest {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("   E-Commerce Platform — Search Algorithm Analysis");
        System.out.println("============================================================\n");

        // ---------------------------------------------------------------- //
        //  Sample Product Catalogue (UNSORTED — for linear search)
        // ---------------------------------------------------------------- //
        Product[] products = {
            new Product(105, "Wireless Headphones",    "Electronics"),
            new Product(302, "Running Shoes",          "Footwear"),
            new Product(47,  "Java Programming Book",  "Books"),
            new Product(218, "Coffee Maker",           "Kitchen"),
            new Product(89,  "Yoga Mat",               "Sports"),
            new Product(561, "Laptop Stand",           "Electronics"),
            new Product(134, "Leather Wallet",         "Accessories"),
            new Product(409, "Bluetooth Speaker",      "Electronics"),
            new Product(23,  "Organic Green Tea",      "Grocery"),
            new Product(677, "Gaming Mouse",           "Electronics"),
        };

        System.out.println("--- Product Catalogue (Unsorted) ---");
        for (Product p : products) System.out.println("  " + p);

        // ---------------------------------------------------------------- //
        //  SECTION 1 — LINEAR SEARCH (Unsorted Array)
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 1 : Linear Search — O(n)");
        System.out.println("============================================================");

        // Test 1a: Search by ID — found (worst case: near end)
        runLinearSearchById(products, 677);

        // Test 1b: Search by ID — found (best case: first element)
        runLinearSearchById(products, 105);

        // Test 1c: Search by ID — not found
        runLinearSearchById(products, 999);

        // Test 1d: Search by name
        runLinearSearchByName(products, "Yoga Mat");
        runLinearSearchByName(products, "Smartwatch");  // not found

        // ---------------------------------------------------------------- //
        //  SECTION 2 — BINARY SEARCH (Sorted Array)
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 2 : Binary Search — O(log n)");
        System.out.println("============================================================");

        // Create a SORTED copy for binary search
        Product[] sortedProducts = products.clone();
        Arrays.sort(sortedProducts);   // uses compareTo (sorted by productId)

        System.out.println("\n--- Product Catalogue (Sorted by productId) ---");
        for (Product p : sortedProducts) System.out.println("  " + p);

        // Test 2a: Binary search (iterative) — found
        runBinarySearch(sortedProducts, 302);

        // Test 2b: Binary search (iterative) — best case (middle element)
        runBinarySearch(sortedProducts, 218);

        // Test 2c: Binary search (iterative) — not found
        runBinarySearch(sortedProducts, 999);

        // Test 2d: Binary search (recursive) — found
        System.out.println("\n[Recursive Binary Search] Searching for productId = 89...");
        int rIdx = SearchAlgorithms.binarySearchRecursive(sortedProducts, 89, 0, sortedProducts.length - 1);
        printResult(sortedProducts, rIdx, 89);

        // ---------------------------------------------------------------- //
        //  SECTION 3 — PERFORMANCE COMPARISON (Large Dataset)
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 3 : Performance Comparison — 100,000 Products");
        System.out.println("============================================================");
        performanceTest(100_000);

        // ---------------------------------------------------------------- //
        //  SECTION 4 — ANALYSIS SUMMARY
        // ---------------------------------------------------------------- //
        System.out.println("\n============================================================");
        System.out.println("  SECTION 4 : Time Complexity Analysis");
        System.out.println("============================================================");
        System.out.println("""
                Algorithm       | Best   | Average  | Worst    | Space
                ----------------|--------|----------|----------|-------
                Linear Search   | O(1)   | O(n)     | O(n)     | O(1)
                Binary Search   | O(1)   | O(log n) | O(log n) | O(1)

                Recommendation for this E-Commerce Platform:
                ─────────────────────────────────────────────
                ✔ USE BINARY SEARCH when searching by productId.
                  - Products can be pre-sorted once (O(n log n)) and then every
                    subsequent search runs in O(log n) — extremely fast even for
                    millions of products.

                ✔ USE LINEAR SEARCH only when:
                  - Searching on an unsorted or frequently changing field
                    (e.g., free-text product name search).
                  - The catalogue is very small (< 100 items).

                ✔ PRODUCTION NOTE: For real e-commerce platforms, consider
                  hash-based lookup O(1) via HashMap<productId, Product> or
                  a full-text search engine (Elasticsearch) for name search.
                """);
    }

    // ------------------------------------------------------------------ //
    //  Helper methods for clean output
    // ------------------------------------------------------------------ //
    private static void runLinearSearchById(Product[] products, int id) {
        long start  = System.nanoTime();
        int  index  = SearchAlgorithms.linearSearch(products, id);
        long elapsed = System.nanoTime() - start;
        System.out.printf("\n[Linear Search by ID=%d]  Time: %d ns%n", id, elapsed);
        printResult(products, index, id);
    }

    private static void runLinearSearchByName(Product[] products, String name) {
        int index = SearchAlgorithms.linearSearchByName(products, name);
        System.out.printf("\n[Linear Search by Name='%s']%n", name);
        if (index != -1) System.out.println("  FOUND   → " + products[index]);
        else             System.out.println("  NOT FOUND");
    }

    private static void runBinarySearch(Product[] sorted, int id) {
        long start   = System.nanoTime();
        int  index   = SearchAlgorithms.binarySearch(sorted, id);
        long elapsed = System.nanoTime() - start;
        System.out.printf("\n[Binary Search by ID=%d]  Time: %d ns%n", id, elapsed);
        printResult(sorted, index, id);
    }

    private static void printResult(Product[] arr, int index, int id) {
        if (index != -1) System.out.println("  FOUND   → " + arr[index] + "  (at index " + index + ")");
        else             System.out.println("  NOT FOUND → productId " + id + " does not exist.");
    }

    private static void performanceTest(int n) {
        // Generate n products sorted by id (1..n)
        Product[] large = new Product[n];
        for (int i = 0; i < n; i++) {
            large[i] = new Product(i + 1, "Product_" + (i + 1), "Category");
        }

        int target = n;  // worst case: last element

        // Linear search — worst case
        long t1 = System.nanoTime();
        SearchAlgorithms.linearSearch(large, target);
        long linearTime = System.nanoTime() - t1;

        // Binary search — O(log n)
        long t2 = System.nanoTime();
        SearchAlgorithms.binarySearch(large, target);
        long binaryTime = System.nanoTime() - t2;

        System.out.printf("%n  Dataset size     : %,d products%n", n);
        System.out.printf("  Target productId : %,d (worst-case position)%n", target);
        System.out.printf("  Linear Search    : %,d ns%n", linearTime);
        System.out.printf("  Binary Search    : %,d ns%n", binaryTime);
        if (linearTime > 0) {
            System.out.printf("  Speed-up Factor  : ~%.1fx faster with Binary Search%n",
                    (double) linearTime / binaryTime);
        }
    }
}
