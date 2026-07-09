package com.cognizant.springlearn.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthController – REST controller that handles authentication requests
 * and issues JSON Web Tokens (JWT).
 *
 * <p>Flow:
 * <ol>
 *   <li>Client sends GET /authenticate with Basic Auth header (username:password)</li>
 *   <li>Controller decodes the Base64 Authorization header</li>
 *   <li>Authenticates credentials via Spring Security's {@link AuthenticationManager}</li>
 *   <li>On success, generates a JWT via {@link JwtUtil} and returns it as JSON</li>
 * </ol>
 *
 * <p>Sample cURL request:
 * <pre>
 *   curl -s -u user:pwd http://localhost:8083/authenticate
 * </pre>
 *
 * <p>Sample Response:
 * <pre>
 *   {"token":"eyJhbGciOiJIUzI1NiJ9..."}
 * </pre>
 */
@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    /** Used to validate username/password against configured security users. */
    @Autowired
    private AuthenticationManager authenticationManager;

    /** Generates JWT tokens for authenticated users. */
    @Autowired
    private JwtUtil jwtUtil;

    // =========================================================
    // GET /authenticate
    // =========================================================

    /**
     * Authenticates the user from the HTTP Basic Authorization header
     * and returns a JWT token if credentials are valid.
     *
     * @param authorizationHeader the raw {@code Authorization} header value
     *                            (e.g., "Basic dXNlcjpwd2Q=")
     * @return HTTP 200 with JWT JSON body, or 401 if credentials are invalid
     */
    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(
            @org.springframework.web.bind.annotation.RequestHeader("Authorization") String authorizationHeader) {

        LOGGER.debug("Start of authenticate() in AuthController.");

        // ---- Step 1: Decode the Basic Auth header ----
        // Authorization: Basic Base64(username:password)
        String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

        // credentials = "username:password"
        String[] parts = credentials.split(":", 2);
        String username = parts[0];
        String password = parts[1];

        LOGGER.debug("Decoded username: {}", username);

        // ---- Step 2: Authenticate via Spring Security ----
        Authentication authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authRequest);
        // If authentication fails, an AuthenticationException is thrown automatically

        // ---- Step 3: Generate JWT ----
        String token = jwtUtil.generateToken(username);
        LOGGER.debug("JWT generated for user: {}", username);

        // ---- Step 4: Return token as JSON ----
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.debug("End of authenticate() in AuthController.");
        return ResponseEntity.ok(response);
    }
}
