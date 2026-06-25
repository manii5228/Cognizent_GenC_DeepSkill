import org.junit.Test;

/**
 * JUnitSetupTest.java — Exercise 1: Setting Up JUnit
 *
 * Verifies that JUnit 4 is correctly configured in the Maven project.
 * A passing test here confirms the dependency is resolved and the
 * test runner can discover and execute JUnit 4 tests.
 *
 * Dependency added to pom.xml:
 *   <dependency>
 *       <groupId>junit</groupId>
 *       <artifactId>junit</artifactId>
 *       <version>4.13.2</version>
 *       <scope>test</scope>
 *   </dependency>
 */
public class JUnitSetupTest {

    /**
     * A simple smoke test that confirms JUnit is wired up correctly.
     * If this test runs and passes, the JUnit setup is successful.
     */
    @Test
    public void testJUnitIsSetupCorrectly() {
        // If the @Test annotation is recognised and the method executes,
        // JUnit 4 is configured correctly in this project.
        System.out.println("[Exercise 1] JUnit setup is working correctly.");
    }

    /**
     * Confirms that the Calculator class (our main production code)
     * is also reachable from the test classpath.
     */
    @Test
    public void testCalculatorIsReachable() {
        Calculator calc = new Calculator();
        System.out.println("[Exercise 1] Calculator class loaded: " + calc.getClass().getName());
    }
}
