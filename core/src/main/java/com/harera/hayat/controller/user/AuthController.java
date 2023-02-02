package com.harera.hayat.controller.user;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.user.auth.FirebaseOauthToken;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.LoginResponse;
import com.harera.hayat.model.user.auth.LogoutRequest;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.model.user.auth.SignupResponse;
import com.harera.hayat.service.user.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Auth", description = "Auth & Oauth API")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login", description = "Login using username & password",
                    tags = "Auth",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized") })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/auth/signup")
    @Operation(summary = "Signup", description = "Signup with firebase token & user form",
                    tags = "Auth",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Bad Request") })
    public ResponseEntity<SignupResponse> signup(SignupRequest signupRequest) {
        return ok(authService.signup(signupRequest));
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "Logout", description = "Logout with token & refresh token",
                    tags = "Auth",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized") })
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ok().build();
    }

    @PostMapping("/oauth/login")
    @Operation(summary = "Login", description = "Login with firebase token",
                    tags = "Auth",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized") })
    public ResponseEntity<LoginResponse> login(
                    @RequestBody OAuthLoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/oauth/firebase-tokens")
    @Operation(summary = "Firebase Token", description = "Generate firebase token",
                    tags = "Auth",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized") })
    public ResponseEntity<FirebaseOauthToken> generateFirebaseToken(
                    @RequestBody LoginRequest loginRequest) {
        return ok(authService.generateFirebaseToken(loginRequest));
    }
}
