package EcommerceSearch;

/**
 * SearchAlgorithms - Implements Linear Search and Binary Search for Products.
 *
 * ============================================================
 *  BIG O NOTATION — QUICK REFERENCE
 * ============================================================
 *  Big O notation describes the UPPER BOUND (worst-case) on how
 *  the runtime of an algorithm grows as the input size (n) grows.
 *  It ignores constants and lower-order terms, focusing on the
 *  dominant growth factor.
 *
 *  Common complexities (best → worst):
 *    O(1)       – Constant   : array index lookup
 *    O(log n)   – Logarithmic: binary search
 *    O(n)       – Linear     : linear search
 *    O(n log n) – Log-linear : merge sort
 *    O(n²)      – Quadratic  : bubble sort
 *
 * ============================================================
 *  SEARCH SCENARIOS
 * ============================================================
 *
 *  LINEAR SEARCH (unsorted array, search by productId)
 *  ┌──────────┬──────────┬────────────────────────────────────┐
 *  │ Scenario │ Big O    │ Description                        │
 *  ├──────────┼──────────┼────────────────────────────────────┤
 *  │ Best     │ O(1)     │ Target is the very first element   │
 *  │ Average  │ O(n/2)≈O(n) │ Target is somewhere in the middle│
 *  │ Worst    │ O(n)     │ Target is last or not present      │
 *  └──────────┴──────────┴────────────────────────────────────┘
 *
 *  BINARY SEARCH (sorted array, search by productId)
 *  ┌──────────┬──────────┬────────────────────────────────────┐
 *  │ Scenario │ Big O    │ Description                        │
 *  ├──────────┼──────────┼────────────────────────────────────┤
 *  │ Best     │ O(1)     │ Target is the middle element       │
 *  │ Average  │ O(log n) │ Target found after log(n) splits   │
 *  │ Worst    │ O(log n) │ Target not present; all splits done│
 *  └──────────┴──────────┴────────────────────────────────────┘
 *
 *  CONCLUSION: For large product catalogues, Binary Search (O(log n))
 *  vastly outperforms Linear Search (O(n)). However, it requires the
 *  array to be sorted beforehand.
 * ============================================================
 */
public class SearchAlgorithms {

    // ------------------------------------------------------------------ //
    //  1. LINEAR SEARCH — O(n) worst case
    //     Works on UNSORTED arrays.
    //     Iterates every element until the target productId is found.
    // ------------------------------------------------------------------ //

    /**
     * Linear Search by productId.
     *
     * @param products  unsorted array of products
     * @param targetId  the productId to search for
     * @return index of the product if found, -1 otherwise
     */
    public static int linearSearch(Product[] products, int targetId) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getProductId() == targetId) {
                return i;          // Found at index i  → O(1) best case
            }
        }
        return -1;                 // Not found         → O(n) worst case
    }

    /**
     * Linear Search by productName (case-insensitive).
     * Useful when the search key is a string (e.g., user types a product name).
     *
     * @param products   unsorted array of products
     * @param targetName the name to search for
     * @return index of the product if found, -1 otherwise
     */
    public static int linearSearchByName(Product[] products, String targetName) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getProductName().equalsIgnoreCase(targetName)) {
                return i;
            }
        }
        return -1;
    }

    // ------------------------------------------------------------------ //
    //  2. BINARY SEARCH — O(log n) worst case
    //     Requires array sorted by productId in ASCENDING order.
    //     Each iteration halves the search space.
    // ------------------------------------------------------------------ //

    /**
     * Binary Search by productId (iterative implementation).
     *
     * @param sortedProducts array sorted by productId (ascending)
     * @param targetId       the productId to search for
     * @return index of the product if found, -1 otherwise
     */
    public static int binarySearch(Product[] sortedProducts, int targetId) {
        int low  = 0;
        int high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;  // avoids integer overflow
            int midId = sortedProducts[mid].getProductId();

            if (midId == targetId) {
                return mid;          // Found → O(1) best case
            } else if (midId < targetId) {
                low = mid + 1;       // target is in right half
            } else {
                high = mid - 1;      // target is in left half
            }
        }
        return -1;                   // Not found
    }

    /**
     * Binary Search by productId (recursive implementation).
     * Functionally identical to the iterative version; kept to
     * demonstrate both styles.
     *
     * @param sortedProducts array sorted by productId
     * @param targetId       productId to find
     * @param low            lower bound of current search range
     * @param high           upper bound of current search range
     * @return index if found, -1 otherwise
     */
    public static int binarySearchRecursive(Product[] sortedProducts,
                                            int targetId, int low, int high) {
        if (low > high) return -1;   // base case: not found

        int mid   = low + (high - low) / 2;
        int midId = sortedProducts[mid].getProductId();

        if (midId == targetId)  return mid;
        if (midId < targetId)   return binarySearchRecursive(sortedProducts, targetId, mid + 1, high);
        return                         binarySearchRecursive(sortedProducts, targetId, low, mid - 1);
    }
}
