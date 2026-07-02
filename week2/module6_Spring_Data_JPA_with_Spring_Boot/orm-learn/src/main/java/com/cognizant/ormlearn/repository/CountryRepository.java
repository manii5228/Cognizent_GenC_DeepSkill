package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CountryRepository is a Spring Data JPA Repository interface.
 *
 * By extending JpaRepository<Country, String>:
 *  - Spring Data JPA automatically generates the implementation at runtime
 *  - No boilerplate SQL or DAO code is needed
 *  - Provides out-of-the-box methods: findAll(), findById(), save(), delete(), etc.
 *
 * Type parameters:
 *  Country - the entity type this repository manages
 *  String  - the type of the primary key (co_code is a String/VARCHAR)
 *
 * @Repository - marks this interface as a Spring-managed repository bean,
 *               also enables exception translation from persistence exceptions
 *               to Spring's DataAccessException hierarchy.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // No custom methods needed for basic findAll() operation.
    // Spring Data JPA provides findAll() automatically through JpaRepository.
}
