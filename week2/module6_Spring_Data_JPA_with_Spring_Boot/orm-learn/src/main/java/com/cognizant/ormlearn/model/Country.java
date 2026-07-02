package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Country is a JPA Entity class that maps to the 'country' table in the ormlearn database.
 *
 * Annotations explained:
 *  @Entity   - Marks this class as a JPA managed entity (maps to a DB table)
 *  @Table    - Specifies the exact table name in the database to map to
 *  @Id       - Marks the field as the primary key of the table
 *  @Column   - Maps the Java field to a specific column name in the DB table
 *
 * JPA / Hibernate Flow:
 *  - Hibernate reads this class and understands the table structure
 *  - Spring Data JPA uses it via CountryRepository to perform CRUD operations
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "co_code")
    private String code;

    @Column(name = "co_name")
    private String name;

    // ---- Getters ----

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // ---- Setters ----

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ---- toString ----

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
