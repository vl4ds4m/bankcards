package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private static final String USER_LOGIN_CLAIM = "userLogin";

    private final SecretKey signingKey;

    private final JwtParser parser;

    public JwtService(@Value("${jwt.signing-key}") String signingKey) {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.parser = Jwts.parser()
                .verifyWith(this.signingKey)
                .build();
    }

    public boolean isTokenValid(String token) {
        if (!parser.isSigned(token)) {
            return false;
        }
        try {
            parser.parse(token);
        } catch (ExpiredJwtException e) {
            return false;
        }
        return true;
    }

    public String extractUserLogin(String token) {
        return extractClaims(token).get(USER_LOGIN_CLAIM, String.class);
    }

    public String generateToken(User user) {
        Instant expiration = Instant.now().plus(1, ChronoUnit.DAYS);
        return Jwts.builder()
                .claims()
                .add(USER_LOGIN_CLAIM, user.getLogin())
                .and()
                .expiration(Date.from(expiration))
                .signWith(signingKey)
                .compact();
    }

    private Claims extractClaims(String token) {
        return parser.parseSignedClaims(token).getPayload();
    }

}
