package com.cognizant.springlearn.service;

import com.cognizant.springlearn.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CountryService – Service layer that provides country data operations.
 *
 * <p>Loads country data from the Spring XML configuration (country.xml)
 * and provides methods to retrieve individual countries or the full list.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    // =========================================================
    // getCountry – Retrieve by code (case-insensitive)
    // =========================================================

    /**
     * Retrieves a specific country by its ISO code (case-insensitive).
     *
     * <p>Loads the countryList bean from country.xml and uses a lambda
     * expression to find the matching country.
     *
     * @param code two-character ISO country code (case-insensitive)
     * @return matching {@link Country} object, or {@code null} if not found
     */
    public Country getCountry(String code) {
        LOGGER.debug("Start of getCountry(). Received code: {}", code);

        // Load all countries from Spring XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        @SuppressWarnings("unchecked")
        List<Country> countryList = (List<Country>) context.getBean("countryList");

        // Lambda expression: case-insensitive match on country code
        Country result = countryList.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        LOGGER.debug("End of getCountry(). Result: {}", result);
        return result;
    }

    // =========================================================
    // getAllCountries – Retrieve all countries
    // =========================================================

    /**
     * Returns all countries configured in country.xml.
     *
     * @return list of all {@link Country} objects
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        LOGGER.debug("Start of getAllCountries().");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countryList = (List<Country>) context.getBean("countryList");

        LOGGER.debug("End of getAllCountries(). Count: {}", countryList.size());
        return countryList;
    }
}
