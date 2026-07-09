package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CountryController – REST controller that exposes country-related endpoints.
 *
 * <p>Endpoints provided:
 * <ul>
 *   <li>GET  /country          – returns India country details (JSON)</li>
 *   <li>GET  /countries        – returns all countries as JSON array</li>
 *   <li>GET  /countries/{code} – returns a specific country by ISO code</li>
 * </ul>
 *
 * <p>Spring's Jackson library automatically converts the {@link Country}
 * objects into JSON responses via the {@code @RestController} annotation.
 */
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    /** CountryService injected by Spring for country lookups and list retrieval. */
    @Autowired
    private CountryService countryService;

    // =========================================================
    // GET /country – Return India
    // =========================================================

    /**
     * Returns India's country details loaded from Spring XML configuration.
     *
     * <p>Uses {@code @RequestMapping} as specified in the assignment.
     * Spring's {@code Jackson2HttpMessageConverter} automatically serializes
     * the returned {@link Country} object to JSON format.
     *
     * @return {@link Country} object representing India
     */
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.debug("Start of getCountryIndia() in CountryController.");

        // Load India bean directly from Spring XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("country", Country.class);

        LOGGER.debug("End of getCountryIndia(). Country: {}", country);
        return country;
    }

    // =========================================================
    // GET /countries – Return all countries
    // =========================================================

    /**
     * Returns all countries configured in country.xml.
     *
     * <p>Delegates to {@link CountryService#getAllCountries()} to load
     * the list from the Spring XML context.
     *
     * @return list of all {@link Country} objects (serialized as JSON array)
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        LOGGER.debug("Start of getAllCountries() in CountryController.");

        List<Country> countries = countryService.getAllCountries();

        LOGGER.debug("End of getAllCountries() in CountryController. Count: {}", countries.size());
        return countries;
    }

    // =========================================================
    // GET /countries/{code} – Return country by code
    // =========================================================

    /**
     * Returns a specific country by its ISO code (case-insensitive).
     *
     * <p>The country code is extracted from the URL path via {@code @PathVariable}.
     * Delegates the lookup to {@link CountryService#getCountry(String)}.
     *
     * @param code two-character ISO country code from the URL path (e.g., "in", "US")
     * @return matching {@link Country} object (serialized as JSON)
     */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) {
        LOGGER.debug("Start of getCountry() in CountryController. Code: {}", code);

        Country country = countryService.getCountry(code);

        LOGGER.debug("End of getCountry() in CountryController. Country: {}", country);
        return country;
    }
}
