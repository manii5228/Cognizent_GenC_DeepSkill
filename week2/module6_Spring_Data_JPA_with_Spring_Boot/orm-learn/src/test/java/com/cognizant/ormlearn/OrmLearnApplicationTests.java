package com.cognizant.ormlearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrmLearnApplicationTests verifies that the Spring ApplicationContext loads
 * successfully with all bean definitions, JPA configuration, and DB connectivity.
 *
 * @SpringBootTest - loads the full application context for integration testing
 */
@SpringBootTest
class OrmLearnApplicationTests {

    /**
     * contextLoads() is a smoke test.
     * If the Spring context fails to start (e.g., bad config, missing bean),
     * this test will fail — giving early feedback on configuration issues.
     */
    @Test
    void contextLoads() {
        // Intentionally empty — context load success is the assertion itself
    }
}
