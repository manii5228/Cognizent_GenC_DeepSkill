package EcommerceSearch;

/**
 * Product - Entity class representing a product on the e-commerce platform.
 *
 * Attributes used for searching:
 *   - productId   : unique numeric identifier
 *   - productName : human-readable name
 *   - category    : product category (e.g., Electronics, Clothing)
 *
 * Implements Comparable<Product> so products can be sorted by productId,
 * which is a pre-condition for Binary Search.
 */
public class Product implements Comparable<Product> {

    private int    productId;
    private String productName;
    private String category;

    // ------------------------------------------------------------------ //
    //  Constructor
    // ------------------------------------------------------------------ //
    public Product(int productId, String productName, String category) {
        this.productId   = productId;
        this.productName = productName;
        this.category    = category;
    }

    // ------------------------------------------------------------------ //
    //  Getters
    // ------------------------------------------------------------------ //
    public int    getProductId()   { return productId;   }
    public String getProductName() { return productName; }
    public String getCategory()    { return category;    }

    // ------------------------------------------------------------------ //
    //  Comparable - sorts by productId (needed for Binary Search)
    // ------------------------------------------------------------------ //
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }

    // ------------------------------------------------------------------ //
    //  Display
    // ------------------------------------------------------------------ //
    @Override
    public String toString() {
        return String.format("Product[id=%-4d | name=%-25s | category=%s]",
                productId, productName, category);
    }
}
