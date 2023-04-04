package com.example.userms.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class  JwtService {

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes =Decoders.BASE64.decode("dGhpcyBpcyBteSBzZWNyZXQga2V5IGZvciBqd3QgYmFzZTY0IGJhc2U2NC4gYmFzZTY0IGJhc2U2NA==");
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String issueToken(JwtCredentials jwtCredentials) {
        log.trace("Issue JWT token to {} for {} seconds", jwtCredentials, 10000);
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date())
                .claim(JwtCredentials.Fields.username, jwtCredentials.getUsername())
                .claim(JwtCredentials.Fields.id, jwtCredentials.getId())
                .claim(JwtCredentials.Fields.role, jwtCredentials.getRole())
                .setExpiration(Date.from(Instant.now().plusSeconds(10000)))
                .setHeader(Map.of("type", "JWT"))
                .signWith(key, SignatureAlgorithm.HS512);
        return jwtBuilder.compact();
    }

}
