package com.harera.hayat.service.user;


import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.LoginResponse;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.model.user.SignupRequest;
import com.harera.hayat.model.user.SignupResponse;

public interface UserService {

    LoginResponse login(LoginRequest loginRequest);
    LoginResponse login(OAuthLoginRequest oAuthLoginRequest);
    SignupResponse signup(SignupRequest signupRequest);
}
