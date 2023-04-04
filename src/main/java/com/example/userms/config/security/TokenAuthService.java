package com.example.userms.config.security;

import static com.example.userms.config.HttpConstants.AUTH_HEADER;
import static com.example.userms.config.HttpConstants.BEARER_AUTH_HEADER;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class TokenAuthService implements AuthService {

    public static final String ROLES_CLAIM = "role";

    private final JwtService jwtService;

    @Override
    public Optional<Authentication> getAuthentication(HttpServletRequest req) {
        return Optional.ofNullable(getHeader(req))
                .filter(this::isBearerAuth)
                .flatMap(this::getAuthenticationBearer);
    }

    private String getHeader(HttpServletRequest req) {
        var header = req.getHeader(AUTH_HEADER);
        if (header == null && !req.getRequestURI().contains("internal")) {
            log.error("'Authorization' header is missing!");
        }
        return header;
    }

    private boolean isBearerAuth(String header) {
        return header.toLowerCase().startsWith(  BEARER_AUTH_HEADER.toLowerCase());
    }

    private Optional<Authentication> getAuthenticationBearer(String header) {
        var token = header.substring(BEARER_AUTH_HEADER.length()).trim();
        var claims = jwtService.parseToken(token);
        log.debug("The claims parsed {}", claims);
        if (claims.getExpiration().before(new Date())) {
            log.trace("Token is expired");
            return Optional.empty();
        }
        return Optional.of(getAuthenticationBearer(claims));
    }

    private Authentication getAuthenticationBearer(Claims claims) {
        var role = claims.get(ROLES_CLAIM, String.class);
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(role.toString()));
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorityList);
    }

}
