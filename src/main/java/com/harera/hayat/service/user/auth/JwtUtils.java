package com.harera.hayat.service.user.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.repository.user.auth.TokenRepository;

import io.jsonwebtoken.Jwts;

@Service
public class JwtUtils {

    private final String secretKey;
    private final TokenRepository tokenRepository;

    public JwtUtils(@Value("${jwt.token.secret.key}") String secretKey,
                    TokenRepository tokenRepository) {
        this.secretKey = secretKey;
        this.tokenRepository = tokenRepository;
    }

    public Long extractUserId(String token) {
        try {
            return Long.parseLong(Jwts.parser().setSigningKey(secretKey)
                            .parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            return null;
        }
    }
}
