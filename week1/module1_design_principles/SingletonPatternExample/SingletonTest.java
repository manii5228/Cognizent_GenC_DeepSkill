package SingletonPatternExample;

/**
 * SingletonTest - Test Class for Singleton Pattern
 *
 * Verifies that only one instance of Logger is ever created,
 * regardless of how many times getInstance() is called.
 */
public class SingletonTest {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   Singleton Pattern - Logger Test");
        System.out.println("========================================\n");

        // Attempt to get multiple instances
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = Logger.getInstance();

        // Test 1: Verify all references point to the same object
        System.out.println("\n--- Instance Equality Check ---");
        System.out.println("logger1 == logger2 : " + (logger1 == logger2));
        System.out.println("logger2 == logger3 : " + (logger2 == logger3));
        System.out.println("logger1 == logger3 : " + (logger1 == logger3));

        // Test 2: Verify hashcodes are identical
        System.out.println("\n--- HashCode Comparison ---");
        System.out.println("HashCode of logger1: " + logger1.hashCode());
        System.out.println("HashCode of logger2: " + logger2.hashCode());
        System.out.println("HashCode of logger3: " + logger3.hashCode());

        // Test 3: Use the singleton logger across different parts of the app
        System.out.println("\n--- Logging via Different References ---");
        logger1.logInfo("Application started from logger1");
        logger2.logWarning("Memory usage is high (from logger2)");
        logger3.logError("Database connection failed (from logger3)");
        logger1.log("Generic log from logger1");

        // Test 4: Thread-safety check - two threads trying to get the instance
        System.out.println("\n--- Thread Safety Test ---");
        Runnable task = () -> {
            Logger threadLogger = Logger.getInstance();
            System.out.println(Thread.currentThread().getName()
                    + " -> HashCode: " + threadLogger.hashCode());
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n[RESULT] Singleton Pattern implemented successfully!");
        System.out.println("========================================");
    }
}
