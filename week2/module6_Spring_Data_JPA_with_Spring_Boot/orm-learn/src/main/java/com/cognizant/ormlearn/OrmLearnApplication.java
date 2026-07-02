package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * OrmLearnApplication is the entry point for the Spring Boot application.
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *  1. @Configuration       - marks this class as a source of Spring bean definitions
 *  2. @EnableAutoConfiguration - tells Spring Boot to auto-configure beans based on
 *                               classpath (e.g., DataSource, EntityManagerFactory, etc.)
 *  3. @ComponentScan       - scans the current package and sub-packages for Spring components
 *                           (@Service, @Repository, @Controller, @Component)
 *
 * Application Layer Architecture:
 *  OrmLearnApplication  →  CountryService  →  CountryRepository  →  MySQL (via Hibernate/JPA)
 */
@SpringBootApplication
public class OrmLearnApplication {

    // SLF4J Logger for logging application events
    // SLF4J is a logging facade; Spring Boot auto-configures Logback as the implementation
    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    /**
     * Static reference to CountryService.
     * Set from the ApplicationContext after Spring Boot starts,
     * so we can call service methods from static main().
     */
    private static CountryService countryService;

    public static void main(String[] args) {
        // Bootstrap the Spring ApplicationContext.
        // This triggers:
        //   - Component scanning
        //   - Auto-configuration (DataSource, JPA, Hibernate)
        //   - Bean creation and dependency injection
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        // Retrieve CountryService bean from the application context
        countryService = context.getBean(CountryService.class);

        LOGGER.info("Inside main");

        // Run the test method to fetch countries from the database
        testGetAllCountries();
    }

    /**
     * Test method to verify Spring Data JPA + Hibernate integration.
     * Calls CountryService which internally uses CountryRepository.findAll()
     * to execute a SELECT query on the 'country' table via Hibernate.
     */
    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("End");
    }
}
