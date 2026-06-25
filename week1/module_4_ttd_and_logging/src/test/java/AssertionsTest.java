import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AssertionsTest.java — Exercise 3: Assertions in JUnit
 *
 * Demonstrates all core JUnit 4 assertion methods:
 *   - assertEquals    : checks two values are equal
 *   - assertTrue      : checks a condition is true
 *   - assertFalse     : checks a condition is false
 *   - assertNull      : checks a reference is null
 *   - assertNotNull   : checks a reference is not null
 *   - assertArrayEquals: checks two arrays are deeply equal
 *   - assertSame      : checks two references point to the same object
 *   - assertNotSame   : checks two references are different objects
 */
public class AssertionsTest {

    // ------------------------------------------------------------------ //
    //  Core assertion demonstrations (from the exercise solution code)
    // ------------------------------------------------------------------ //

    @Test
    public void testAssertions() {
        System.out.println("[Exercise 3] Running testAssertions...");

        // assertEquals: verifies that expected value equals actual value
        assertEquals("2 + 3 should equal 5", 5, 2 + 3);

        // assertTrue: verifies that the condition evaluates to true
        assertTrue("5 should be greater than 3", 5 > 3);

        // assertFalse: verifies that the condition evaluates to false
        assertFalse("5 should NOT be less than 3", 5 < 3);

        // assertNull: verifies that the object reference is null
        assertNull("null literal should be null", null);

        // assertNotNull: verifies that the object reference is NOT null
        assertNotNull("new Object() should not be null", new Object());

        System.out.println("[Exercise 3] testAssertions PASSED.");
    }

    // ------------------------------------------------------------------ //
    //  Additional assertion types
    // ------------------------------------------------------------------ //

    @Test
    public void testAssertEqualsWithDelta() {
        // assertEquals with delta for floating-point comparisons
        // (avoids floating-point precision issues)
        assertEquals("Pi should be approximately 3.14", 3.14159, Math.PI, 0.001);
        System.out.println("[Exercise 3] testAssertEqualsWithDelta PASSED.");
    }

    @Test
    public void testAssertArrayEquals() {
        // assertArrayEquals: checks each element of two arrays
        int[] expected = {1, 2, 3, 4, 5};
        int[] actual   = {1, 2, 3, 4, 5};
        assertArrayEquals("Arrays should be element-wise equal", expected, actual);
        System.out.println("[Exercise 3] testAssertArrayEquals PASSED.");
    }

    @Test
    public void testAssertSameAndNotSame() {
        String s1 = "hello";
        String s2 = s1;           // same reference
        String s3 = new String("hello");   // different object, same content

        // assertSame: checks reference identity (==)
        assertSame("s1 and s2 should point to same object", s1, s2);

        // assertNotSame: checks references are NOT identical
        assertNotSame("s1 and s3 are different objects despite equal content", s1, s3);

        System.out.println("[Exercise 3] testAssertSameAndNotSame PASSED.");
    }

    @Test
    public void testAssertEqualsString() {
        String expected = "Cognizant GenC";
        String actual   = "Cognizant" + " " + "GenC";
        assertEquals("String concatenation should match expected", expected, actual);
        System.out.println("[Exercise 3] testAssertEqualsString PASSED.");
    }

    @Test(expected = ArithmeticException.class)
    public void testExpectedException() {
        // @Test(expected=...) verifies that a specific exception is thrown
        int result = 10 / 0;   // should throw ArithmeticException
    }
}
