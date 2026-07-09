package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringLearnApplication – Main entry point for the Spring Learn application.
 *
 * <p>Demonstrates:
 * <ul>
 *   <li>Spring Boot application bootstrap via {@code @SpringBootApplication}</li>
 *   <li>Loading Spring XML configuration with {@link ClassPathXmlApplicationContext}</li>
 *   <li>Retrieving a bean from the application context</li>
 * </ul>
 *
 * <p>{@code @SpringBootApplication} is a convenience annotation that combines:
 * <ul>
 *   <li>{@code @Configuration} – marks the class as a source of bean definitions</li>
 *   <li>{@code @EnableAutoConfiguration} – enables Spring Boot's auto-configuration</li>
 *   <li>{@code @ComponentScan} – scans for components in the current package and sub-packages</li>
 * </ul>
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    // =========================================================
    // Main Method
    // =========================================================

    /**
     * Application entry point.
     *
     * <p>Logs start and end markers so that the main() invocation is visible
     * in the console output during execution.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        LOGGER.debug("Start of main() method in SpringLearnApplication.");

        SpringApplication.run(SpringLearnApplication.class, args);

        // Display country loaded from Spring XML configuration
        displayCountry();

        LOGGER.debug("End of main() method in SpringLearnApplication.");
    }

    // =========================================================
    // displayCountry
    // =========================================================

    /**
     * Reads the 'country' bean from the Spring XML configuration file (country.xml)
     * and logs the country details.
     *
     * <p>Uses {@link ClassPathXmlApplicationContext} to bootstrap a standalone
     * Spring application context from the classpath XML file.
     */
    private static void displayCountry() {
        LOGGER.debug("Start of displayCountry() method.");

        // Load the Spring XML application context from classpath
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // Retrieve the 'country' bean (India) from context
        Country country = context.getBean("country", Country.class);

        // Log the country details
        LOGGER.debug("Country : {}", country.toString());

        LOGGER.debug("End of displayCountry() method.");
    }
}
