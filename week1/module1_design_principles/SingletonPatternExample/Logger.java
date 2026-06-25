package SingletonPatternExample;

/**
 * Logger - Singleton Class
 *
 * Ensures only one instance of Logger exists throughout the application.
 * Uses "lazy initialization with double-checked locking" for thread safety.
 */
public class Logger {

    // Step 1: Private static instance of itself (volatile for thread-safety)
    private static volatile Logger instance = null;

    // Step 2: Private constructor prevents external instantiation
    private Logger() {
        System.out.println("Logger instance created.");
    }

    // Step 3: Public static method to get the single instance
    public static Logger getInstance() {
        if (instance == null) {                        // First check (no locking)
            synchronized (Logger.class) {
                if (instance == null) {                // Second check (with locking)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // Logging utility methods
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }

    public void logInfo(String message) {
        System.out.println("[INFO]  " + message);
    }

    public void logWarning(String message) {
        System.out.println("[WARN]  " + message);
    }

    public void logError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
