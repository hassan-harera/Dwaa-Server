package com.harera.hayatserver.service.user;


import com.harera.hayatserver.model.user.LoginRequest;
import com.harera.hayatserver.model.user.LoginResponse;
import com.harera.hayatserver.model.user.OAuthLoginRequest;
import com.harera.hayatserver.model.user.SignupRequest;
import com.harera.hayatserver.model.user.SignupResponse;

public interface UserService {

    LoginResponse login(LoginRequest loginRequest);
    LoginResponse login(OAuthLoginRequest oAuthLoginRequest);
    SignupResponse signup(SignupRequest signupRequest);
}
