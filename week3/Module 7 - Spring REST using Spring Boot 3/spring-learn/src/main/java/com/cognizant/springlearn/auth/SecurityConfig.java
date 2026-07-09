package com.cognizant.springlearn.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig – Spring Security configuration for the spring-learn application.
 *
 * <p>Security rules:
 * <ul>
 *   <li>{@code /authenticate} – publicly accessible (no authentication required);
 *       credentials are read and validated manually in {@link AuthController}</li>
 *   <li>All other endpoints – publicly accessible for this demo application</li>
 * </ul>
 *
 * <p>Session management is set to STATELESS since JWT-based authentication
 * does not rely on HTTP sessions.
 *
 * <p>CSRF protection is disabled for REST API compatibility (standard practice
 * for stateless REST services consumed by non-browser clients).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // =========================================================
    // SecurityFilterChain
    // =========================================================

    /**
     * Configures the HTTP security filter chain.
     *
     * @param http the {@link HttpSecurity} builder
     * @return configured {@link SecurityFilterChain}
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless REST API
            .csrf(csrf -> csrf.disable())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // /authenticate is open – AuthController validates credentials manually
                .requestMatchers("/authenticate").permitAll()
                // All other endpoints are publicly accessible for this demo
                .anyRequest().permitAll()
            )

            // Disable default form login and HTTP Basic pop-up
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // Stateless session – no HttpSession will be created
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }

    // =========================================================
    // AuthenticationManager Bean
    // =========================================================

    /**
     * Exposes the {@link AuthenticationManager} as a Spring bean so it can
     * be injected into {@link AuthController}.
     *
     * @param config the auto-configured {@link AuthenticationConfiguration}
     * @return the default {@link AuthenticationManager}
     * @throws Exception if retrieval fails
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
