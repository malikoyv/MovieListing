package com.malikoyv.movielisting.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private static final String secreteKey = "S6u0hXfDQ8s8yCQs0BQgZQa6";
    public String extractUsername(String jwt) {
        return null;
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
