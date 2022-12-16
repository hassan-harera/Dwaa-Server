package com.harera.hayat.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.model.user.auth.SignupResponse;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.LoginResponse;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;

public interface UserService extends UserDetailsService {

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse login(OAuthLoginRequest oAuthLoginRequest);

    SignupResponse signup(SignupRequest signupRequest);
}
