package com.harera.hayat.service.user;

import static com.harera.hayat.util.StringRegexUtils.isEmail;
import static com.harera.hayat.util.StringRegexUtils.isPhoneNumber;
import static com.harera.hayat.util.StringRegexUtils.isUsername;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.exception.SignupException;
import com.harera.hayat.model.user.FirebaseUser;
import com.harera.hayat.model.user.User;
import com.harera.hayat.model.user.auth.InvalidateLoginRequest;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.LoginResponse;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.model.user.auth.SignupResponse;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.repository.user.auth.TokenRepository;
import com.harera.hayat.service.firebase.FirebaseService;
import com.harera.hayat.service.user.auth.AuthService;
import com.harera.hayat.service.user.auth.JwtUtils;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final TokenRepository tokenRepository;
    private final FirebaseService firebaseService;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    UserServiceImpl(UserRepository userRepository, AuthService authService,
                    PasswordEncoder passwordEncoder, UserValidation userValidation,
                    TokenRepository tokenRepository, FirebaseService firebaseService,
                    ModelMapper modelMapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
        this.tokenRepository = tokenRepository;
        this.firebaseService = firebaseService;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        userValidation.validate(loginRequest);

        long userId = getUserId(loginRequest.getSubject());
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));

        if (!Objects.equals(user.getDeviceToken(), loginRequest.getDeviceToken())) {
            user.setDeviceToken(loginRequest.getDeviceToken());
            userRepository.save(user);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(authService.generateToken(user));
        loginResponse.setRefreshToken(authService.generateRefreshToken(user));

        return loginResponse;
    }

    public LoginResponse login(OAuthLoginRequest oAuthLoginRequest) {
        userValidation.validate(oAuthLoginRequest);
        //TODO
        return null;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        userValidation.validate(signupRequest);
        FirebaseUser firebaseUser = firebaseService.createUser(signupRequest);
        if (firebaseUser == null) {
            throw new SignupException("User creation failed");
        }

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUid(firebaseUser.getUid());
        user.setUsername(firebaseUser.getUid());

        userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                        () -> new EntityNotFoundException(User.class, userId));
    }

    private long getUserId(String subject) {
        long userId = 0;
        if (isPhoneNumber(subject)) {
            userId = userRepository.findByMobile(subject).getId();
        } else if (isEmail(subject)) {
            userId = userRepository.findByEmail(subject).getId();
        } else if (isUsername(subject)) {
            userId = userRepository.findByUsername(subject).getId();
        }
        return userId;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
        try {
            long userId = Integer.parseInt(username);
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public LoginResponse refresh(String refreshToken) {
        String usernameOrMobile = jwtUtils.extractUserSubject(refreshToken);
        final User user = (User) loadUserByUsername(usernameOrMobile);
        jwtUtils.validateRefreshToken(user, refreshToken);
        LoginResponse authResponse = new LoginResponse();
        authResponse.setToken(authService.generateToken(user));
        authResponse.setRefreshToken(authService.generateRefreshToken(user));
        return authResponse;
    }

    public void invalidate(InvalidateLoginRequest request) {
        String usernameOrMobile = jwtUtils.extractUserSubject(request.getToken());
        final User user = (User) loadUserByUsername(usernameOrMobile);
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
