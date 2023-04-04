package com.example.userms.config.security;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {

    /**
     * Extract authentication object out of request.
     *
     * @param httpServletRequest : the http servlet request
     * @return : extracted Authentication
     */
    Optional<Authentication> getAuthentication(HttpServletRequest httpServletRequest);
}
