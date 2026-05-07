package org.hei.federationagribackend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ApiKeyValidator {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String VALID_API_KEY = "agri-secure-key";

    public void validate(HttpServletRequest request) {
        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey == null || !apiKey.equals(VALID_API_KEY)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
    }
}