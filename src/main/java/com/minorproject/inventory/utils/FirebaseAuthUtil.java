package com.minorproject.inventory.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.InvalidToken;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.exception.TokenNotFound;
import com.minorproject.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class FirebaseAuthUtil {
    
    private final UserRepository userRepository;
    
    /**
     * Validates a token string and extracts the associated Firebase token
     *
     * @param token The Authorization header value
     * @return The decoded Firebase token
     * @throws TokenNotFound if the token is null, blank, or doesn't start with "Bearer"
     * @throws InvalidToken  if the token is invalid or cannot be verified
     */
    public FirebaseToken validateAndDecodeToken(String token) {
        if (token == null || token.isBlank() || !token.startsWith("Bearer")) {
            log.debug("Invalid token format: {}", token);
            throw new TokenNotFound();
        }
        
        String actualToken = token.substring(7);
        return verifyAndDecodeToken(actualToken);
    }
    
    /**
     * Gets the authenticated user from a token
     *
     * @param token The Authorization header value
     * @return The User entity
     * @throws TokenNotFound             if the token is invalid
     * @throws ResourceNotFoundException if no user exists with the email from the token
     */
    public User getUserFromToken(String token) {
        FirebaseToken decodedToken = validateAndDecodeToken(token);
        String email = decodedToken.getEmail();
        
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
    
    /**
     * Verifies a Firebase ID token
     *
     * @param token The raw token string (without "Bearer" prefix)
     * @return The decoded Firebase token
     * @throws InvalidToken if the token is invalid or cannot be verified
     */
    private FirebaseToken verifyAndDecodeToken(String token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            log.error("Error decoding token: {}", e.getMessage());
            throw new InvalidToken();
        }
    }
    
    public FirebaseToken validateTokenWithoutPrefix(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            log.debug("Token is null or blank");
            throw new TokenNotFound();
        }
        return verifyAndDecodeToken(rawToken);
    }
}