package com.harera.hayat.gateway.authorization.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.firebase.internal.FirebaseService;
import com.harera.hayat.common.repository.user.TokenRepository;
import com.harera.hayat.common.repository.user.UserRepository;
import com.harera.hayat.common.service.auth.JwtService;
import com.harera.hayat.common.service.auth.JwtUtils;
import com.harera.hayat.common.service.auth.UserUtils;

public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final TokenRepository tokenRepository;
    private final FirebaseService firebaseService;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final JwtService jwtService;
    private final UserUtils userUtils;

    public UserService(UserRepository userRepository, AuthService authService,
                    PasswordEncoder passwordEncoder, UserValidation userValidation,
                    TokenRepository tokenRepository, FirebaseService firebaseService,
                    ModelMapper modelMapper, JwtUtils jwtUtils, JwtService jwtService,
                    UserUtils userUtils) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
        this.tokenRepository = tokenRepository;
        this.firebaseService = firebaseService;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.jwtService = jwtService;
        this.userUtils = userUtils;
    }

}
