package com.harera.hayat.service.user.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.user.Role;
import com.harera.hayat.model.user.User;
import com.harera.hayat.model.user.auth.InvalidateLoginRequest;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.LoginResponse;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.repository.user.auth.TokenRepository;
import com.harera.hayat.service.user.UserService;
import com.harera.hayat.service.user.UserValidation;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    private final String tokenExpire;
    private final String refreshTokenExpire;
    private final String secretKey;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthService(@Value("${jwt.token.expire}") String tokenExpire,
                    @Value("${jwt.token.refresh.expire}") String refreshTokenExpire,
                    @Value("${jwt.token.secret.key}") String secretKey,
                    UserService userService, UserRepository userRepository,
                    AuthenticationManager authenticationManager,
                    UserValidation userValidation, JwtUtils jwtUtils,
                    TokenRepository tokenRepository) {
        this.tokenExpire = tokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.secretKey = secretKey;
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userValidation = userValidation;
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

    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getSubject(), loginRequest.getPassword()));
        final User user =
                        (User) userService.loadUserByUsername(loginRequest.getSubject());
        final String requestDeviceToken = loginRequest.getDeviceToken();
        if (!Objects.equals(user.getDeviceToken(), requestDeviceToken)) {
            user.setDeviceToken(requestDeviceToken);
            userRepository.save(user);
        }
        LoginResponse authResponse = new LoginResponse();
        authResponse.setToken(generateToken(user));
        authResponse.setRefreshToken(generateRefreshToken(user));
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
        final String userSubject = StringUtils.isNotEmpty(user.getUsername())
                        ? user.getUsername() : user.getMobile();
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

    public LoginResponse refresh(String refreshToken) {
        String usernameOrMobile = jwtUtils.extractUserSubject(refreshToken);
        final User user = (User) userService.loadUserByUsername(usernameOrMobile);
        jwtUtils.validateRefreshToken(user, refreshToken);
        LoginResponse authResponse = new LoginResponse();
        authResponse.setToken(generateToken(user));
        authResponse.setRefreshToken(generateRefreshToken(user));
        return authResponse;
    }

    public void invalidate(InvalidateLoginRequest request) {
        String usernameOrMobile = jwtUtils.extractUserSubject(request.getToken());
        final User user = (User) userService.loadUserByUsername(usernameOrMobile);
        // resetting user device token in case of logout
        if (StringUtils.isNotEmpty(user.getDeviceToken())) {
            user.setDeviceToken(null);
            userRepository.save(user);
        }
        jwtUtils.validateToken(user, request.getToken());
        tokenRepository.removeUserToken(request.getToken());
        if (StringUtils.isNotEmpty(request.getRefreshToken())) {
            jwtUtils.validateRefreshToken(user, request.getRefreshToken());
            tokenRepository.removeUserRefreshToken(request.getRefreshToken());
        }
    }
}
