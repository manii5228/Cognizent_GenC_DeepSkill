package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CountryService contains the business logic for country-related operations.
 *
 * @Service  - marks this class as a Spring-managed service bean (component scan picks it up)
 * @Autowired - injects the CountryRepository dependency automatically by Spring IoC container
 *
 * Layer Responsibility:
 *  - OrmLearnApplication (Entry Point)
 *      → CountryService     (Business Logic Layer)
 *          → CountryRepository  (Data Access Layer)
 *              → MySQL DB via Hibernate/JPA
 */
@Service
public class CountryService {

    // Spring automatically injects a CountryRepository implementation at runtime
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Retrieves all country records from the database.
     *
     * @Transactional ensures this method runs within a database transaction.
     * If an exception occurs, the transaction is automatically rolled back.
     *
     * @return list of all Country entities from the 'country' table
     */
    @Transactional
    public List<Country> getAllCountries() {
        // Delegates to Spring Data JPA's built-in findAll() — no SQL needed!
        return countryRepository.findAll();
    }
}
