package com.cognizant.springlearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SpringLearnApplicationTests – Integration tests for the Spring Learn application.
 *
 * <p>Verifies that the Spring application context loads successfully,
 * which implicitly checks all bean configurations, component scanning,
 * and auto-configuration.
 */
@SpringBootTest
class SpringLearnApplicationTests {

    /**
     * Tests that the application context loads without errors.
     *
     * <p>If any bean definition, auto-configuration, or dependency injection
     * is incorrect, this test will fail with a context load exception.
     */
    @Test
    void contextLoads() {
        // The test passes if the application context loads without throwing an exception.
    }
}
