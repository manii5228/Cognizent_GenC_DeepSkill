import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggingExample.java
 * Logging Exercise 1 — Demonstrating SLF4J logging levels with Logback.
 *
 * SLF4J (Simple Logging Facade for Java) provides a unified logging API.
 * Logback is the concrete logging implementation bound at runtime.
 *
 * Log Levels (lowest → highest severity):
 *   TRACE  – Very fine-grained diagnostic information (dev only)
 *   DEBUG  – Diagnostic information useful during development
 *   INFO   – General application flow information
 *   WARN   – Something unexpected happened but the app can continue
 *   ERROR  – A serious error that requires attention
 */
public class LoggingExample {

    // Logger is obtained via LoggerFactory; class name is used as the logger name
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("  SLF4J + Logback — Logging Level Demonstration");
        System.out.println("============================================================\n");

        // TRACE — lowest level; most verbose; typically disabled in production
        logger.trace("TRACE: Application trace point reached — fine-grained diagnostic info");

        // DEBUG — useful for developers during testing and debugging
        logger.debug("DEBUG: Processing started — user input validated successfully");

        // INFO — routine application events; always visible in production
        logger.info("INFO:  Application started successfully on port 8080");

        // WARN — something unexpected; application can still run
        logger.warn("WARN:  Configuration value missing for 'timeout'; using default: 30s");

        // ERROR — serious failure; immediate attention required
        logger.error("ERROR: Database connection failed — retrying in 5 seconds");

        // Parameterised logging (preferred over string concatenation — no performance cost
        // when the level is disabled)
        String username = "prathibha";
        int    attempts = 3;
        logger.warn("WARN:  User '{}' failed to authenticate after {} attempt(s)", username, attempts);

        // Logging with exception stack trace
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("ERROR: Arithmetic exception occurred during calculation", e);
        }

        logger.info("INFO:  Logging demonstration complete.");
    }
}
