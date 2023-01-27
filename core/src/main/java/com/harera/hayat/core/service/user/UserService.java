package com.harera.hayat.core.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.harera.hayat.core.model.user.auth.LoginRequest;
import com.harera.hayat.core.model.user.auth.LoginResponse;
import com.harera.hayat.core.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.core.model.user.auth.SignupRequest;
import com.harera.hayat.core.model.user.auth.SignupResponse;

public interface UserService extends UserDetailsService {

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse login(OAuthLoginRequest oAuthLoginRequest);

    SignupResponse signup(SignupRequest signupRequest);
}
