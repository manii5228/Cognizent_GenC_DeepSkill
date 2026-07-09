package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController – REST controller that exposes a simple greeting endpoint.
 *
 * <p>Demonstrates a basic Spring MVC REST controller with:
 * <ul>
 *   <li>{@code @RestController} – combines {@code @Controller} and {@code @ResponseBody}</li>
 *   <li>{@code @GetMapping} – maps HTTP GET requests to handler methods</li>
 * </ul>
 *
 * <p>Sample Request : GET http://localhost:8083/hello
 * <p>Sample Response: Hello World!!
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    // =========================================================
    // GET /hello
    // =========================================================

    /**
     * Returns a hard-coded greeting string.
     *
     * <p>Start and end log statements are included so that every invocation
     * of this method is visible in the application logs.
     *
     * @return the greeting text "Hello World!!"
     */
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.debug("Start of sayHello() in HelloController.");

        String response = "Hello World!!";

        LOGGER.debug("End of sayHello() in HelloController. Response: {}", response);
        return response;
    }
}
