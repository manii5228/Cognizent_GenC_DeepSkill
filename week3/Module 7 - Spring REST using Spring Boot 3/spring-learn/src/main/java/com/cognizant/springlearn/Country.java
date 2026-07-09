package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Country – Domain model representing a country with ISO code and name.
 *
 * <p>This class is configured as a Spring bean in country.xml.
 * All constructors, getters, and setters include DEBUG-level logging
 * so that the bean lifecycle is visible in application logs.
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    /** Two-character ISO 3166-1 alpha-2 country code (e.g., "IN", "US"). */
    private String code;

    /** Full display name of the country (e.g., "India", "United States"). */
    private String name;

    // =========================================================
    // Constructor
    // =========================================================

    /**
     * No-argument constructor required by Spring for XML bean instantiation.
     * A DEBUG log is included to confirm when the bean is created.
     */
    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    // =========================================================
    // Getters
    // =========================================================

    /**
     * Returns the ISO country code.
     *
     * @return two-character country code
     */
    public String getCode() {
        LOGGER.debug("Inside getCode(). code = {}", code);
        return code;
    }

    /**
     * Returns the country display name.
     *
     * @return country name
     */
    public String getName() {
        LOGGER.debug("Inside getName(). name = {}", name);
        return name;
    }

    // =========================================================
    // Setters
    // =========================================================

    /**
     * Sets the ISO country code.
     *
     * @param code two-character ISO code
     */
    public void setCode(String code) {
        LOGGER.debug("Inside setCode(). code = {}", code);
        this.code = code;
    }

    /**
     * Sets the country display name.
     *
     * @param name country name
     */
    public void setName(String name) {
        LOGGER.debug("Inside setName(). name = {}", name);
        this.name = name;
    }

    // =========================================================
    // toString
    // =========================================================

    /**
     * Returns a human-readable representation of this Country object.
     *
     * @return string in the form "Country{code='IN', name='India'}"
     */
    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
