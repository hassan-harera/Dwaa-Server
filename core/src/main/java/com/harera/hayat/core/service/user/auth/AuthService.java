package com.harera.hayat.core.service.user.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harera.hayat.core.model.user.Role;
import com.harera.hayat.core.model.user.User;
import com.harera.hayat.core.model.user.auth.LoginResponse;
import com.harera.hayat.core.repository.UserRepository;
import com.harera.hayat.core.repository.user.auth.TokenRepository;
import com.harera.hayat.core.service.user.UserValidation;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    private final String tokenExpire;
    private final String refreshTokenExpire;
    private final String secretKey;
    private final UserRepository userRepository;
    private final com.harera.hayat.core.service.user.auth.JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthService(@Value("${jwt.token.expire}") String tokenExpire,
                    @Value("${jwt.token.refresh.expire}") String refreshTokenExpire,
                    @Value("${jwt.token.secret.key}") String secretKey,
                    UserRepository userRepository, UserValidation ignoredUserValidation,
                    com.harera.hayat.core.service.user.auth.JwtUtils jwtUtils,
                    TokenRepository tokenRepository) {
        this.tokenExpire = tokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.secretKey = secretKey;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.tokenRepository = tokenRepository;
    }

    public LoginResponse guestAuthenticate() {
        LoginResponse authResponse = new LoginResponse();
        String jwt = createToken("GUEST", -1, getGuestClaims());
        authResponse.setRefreshToken(jwt);
        authResponse.setToken(jwt);
        return authResponse;
    }

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("id", user.getId());
        claims.put("uid", user.getUid());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("mobile", user.getMobile());
        claims.put("role", Role.GUEST);
        return claims;
    }

    private Map<String, Object> getGuestClaims() {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("id", 0);
        claims.put("role", Role.GUEST);

        return claims;
    }

    public String generateToken(User user) {
        final String userSubject = String.valueOf(user.getId());
        final String token = createToken(userSubject, Long.valueOf(tokenExpire),
                        getClaims(user));
        tokenRepository.addToken(user.getId(), token);
        return token;
    }

    public String generateRefreshToken(User user) {
        final String token = createToken(user.getUsername(),
                        Long.valueOf(refreshTokenExpire), null);
        tokenRepository.addRefreshToken(user.getId(), token);
        return token;
    }

    private String createToken(String username, long expireInMillis,
                    Map<String, Object> claims) {
        final JwtBuilder jwtBuilder = Jwts.builder();
        if (claims != null) {
            jwtBuilder.setClaims(claims);
        }

        if (expireInMillis != -1) {
            jwtBuilder.setExpiration(
                            new Date(System.currentTimeMillis() + expireInMillis));
        }

        jwtBuilder.setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                        .signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }

    public User getUserForAuthorization(String token) {
        Long userId = jwtUtils.extractUserId(token.substring(7));
        return userRepository.findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                        "User not found with id : " + userId));
    }

    public User getRequestUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        if (principal instanceof User user)
            return user;
        throw new JwtException("Invalid token");
    }
}
