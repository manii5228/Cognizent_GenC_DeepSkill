# Module 6 – Spring Data JPA with Spring Boot & Hibernate

## Project: orm-learn

A hands-on Spring Boot application demonstrating the use of **Spring Data JPA** and **Hibernate** to persist and retrieve data from a **MySQL** database.

---

## 🗂️ Project Structure

```
orm-learn/
├── pom.xml                                          # Maven config (Spring Boot parent, JPA, MySQL)
└── src/
    ├── main/
    │   ├── java/com/cognizant/ormlearn/
    │   │   ├── OrmLearnApplication.java             # Spring Boot entry point
    │   │   ├── model/
    │   │   │   └── Country.java                     # JPA Entity → maps to 'country' table
    │   │   ├── repository/
    │   │   │   └── CountryRepository.java           # Spring Data JPA repository
    │   │   └── service/
    │   │       └── CountryService.java              # Business logic layer
    │   └── resources/
    │       ├── application.properties               # DB + Hibernate + logging config
    │       └── db_setup.sql                         # SQL script to create schema & seed data
    └── test/
        └── java/com/cognizant/ormlearn/
            └── OrmLearnApplicationTests.java        # Context load smoke test
```

---

## ⚙️ Pre-requisites & Setup

### 1. Create MySQL Schema

```bash
mysql -u root -p
```

```sql
CREATE SCHEMA IF NOT EXISTS ormlearn;
USE ormlearn;

CREATE TABLE country (
    co_code VARCHAR(2)  PRIMARY KEY,
    co_name VARCHAR(50) NOT NULL
);

INSERT INTO country VALUES ('IN', 'India');
INSERT INTO country VALUES ('US', 'United States of America');
```

> Alternatively, run `src/main/resources/db_setup.sql` directly in MySQL Workbench.

### 2. Build the Project

```bash
mvn clean package
```

### 3. Run the Application

Run `OrmLearnApplication.java` as a Java application from Eclipse, or:

```bash
mvn spring-boot:run
```

---

## 🧠 Key Concepts

### JPA vs Hibernate vs Spring Data JPA

| | JPA | Hibernate | Spring Data JPA |
|---|---|---|---|
| **What it is** | Specification (JSR 338) | ORM Framework (JPA implementation) | Abstraction over JPA/Hibernate |
| **Has implementation?** | ❌ No | ✅ Yes | ✅ Yes (uses Hibernate underneath) |
| **Purpose** | Defines standard API for ORM | Actual database mapping & querying | Removes boilerplate data access code |
| **Example** | `@Entity`, `@Table`, `EntityManager` | `Session`, `SessionFactory`, HQL | `JpaRepository`, `findAll()`, `findById()` |

### In simple terms:
- **JPA** = The rulebook (specification)
- **Hibernate** = The player that follows the rulebook (implementation)
- **Spring Data JPA** = The team manager that removes repetitive work on top of Hibernate

### References
- [What is the difference between Hibernate and Spring Data JPA?](https://dzone.com/articles/what-is-the-difference-between-hibernate-and-sprin-1)
- [Intro to JPA - JavaWorld](https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)

---

## 📌 Annotation Reference

| Annotation | Location | Purpose |
|---|---|---|
| `@SpringBootApplication` | Main class | Enables auto-config, component scan, bean registration |
| `@Entity` | Country.java | Marks class as a JPA entity |
| `@Table(name="country")` | Country.java | Maps entity to 'country' DB table |
| `@Id` | Country.java | Marks primary key field |
| `@Column(name="co_code")` | Country.java | Maps field to DB column |
| `@Repository` | CountryRepository | Marks as Spring data access bean |
| `@Service` | CountryService | Marks as Spring business logic bean |
| `@Autowired` | CountryService | Injects CountryRepository |
| `@Transactional` | getAllCountries() | Ensures DB operation runs in a transaction |
