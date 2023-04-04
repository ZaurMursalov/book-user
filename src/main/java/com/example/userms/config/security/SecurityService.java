package com.example.userms.config.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final ModelMapper mapper;

    /**
     * Get the current session credentials.
     *
     * @return the SessionCredentials.
     */
    public JwtCredentials getCurrentJwtCredentials() {
        var securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> mapper.map(authentication.getPrincipal(), JwtCredentials.class))
                .orElseThrow(RuntimeException::new);
    }

    public String getEmail() {
        return getCurrentJwtCredentials().getUsername();
    }

}
