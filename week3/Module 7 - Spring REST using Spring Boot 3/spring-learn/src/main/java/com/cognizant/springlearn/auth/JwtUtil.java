package com.cognizant.springlearn.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtUtil – Utility class for generating JSON Web Tokens (JWT).
 *
 * <p>Uses the JJWT (Java JWT) library to create signed tokens with:
 * <ul>
 *   <li>Subject: the authenticated username</li>
 *   <li>Issued-at timestamp</li>
 *   <li>Expiry timestamp (configurable via {@code jwt.token.validity})</li>
 *   <li>HMAC-SHA256 signature</li>
 * </ul>
 *
 * <p>JWT Structure (Base64-encoded, dot-separated):
 * <pre>
 *   Header.Payload.Signature
 *   eyJhbGciOiJIUzI1NiJ9 . eyJzdWIiOiJ1c2VyIiwiaWF0Ijox... . t3LRvlCV-...
 * </pre>
 */
@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    /** Token validity period in milliseconds (default: 1200000 = 20 minutes). */
    @Value("${jwt.token.validity:1200000}")
    private long tokenValidity;

    /**
     * A fixed secret key used for signing JWTs.
     * In production this should be loaded from a secure vault / environment variable.
     * Must be at least 256 bits for HS256.
     */
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(
                "cognizant-spring-learn-jwt-secret-key-256bits!!"
                        .getBytes()
            );

    // =========================================================
    // generateToken
    // =========================================================

    /**
     * Generates a signed JWT token for the given username.
     *
     * @param username the authenticated user's name to embed as the JWT subject
     * @return compact serialized JWT string
     */
    public String generateToken(String username) {
        LOGGER.debug("Start of generateToken(). Username: {}", username);

        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + tokenValidity);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

        LOGGER.debug("End of generateToken(). Token generated successfully.");
        return token;
    }
}
