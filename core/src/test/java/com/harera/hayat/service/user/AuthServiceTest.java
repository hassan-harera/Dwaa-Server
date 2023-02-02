package com.harera.hayat.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.harera.hayat.config.NotNullableMapper;
import com.harera.hayat.model.user.AppFirebaseToken;
import com.harera.hayat.model.user.AppFirebaseUser;
import com.harera.hayat.model.user.User;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.model.user.auth.SignupResponse;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.repository.user.auth.TokenRepository;
import com.harera.hayat.service.firebase.FirebaseService;
import com.harera.hayat.service.user.auth.AuthService;
import com.harera.hayat.service.user.auth.AuthValidation;
import com.harera.hayat.service.user.auth.JwtService;
import com.harera.hayat.service.user.auth.JwtUtils;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private FirebaseService firebaseService;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private AuthValidation authValidation;
    @Mock
    private UserUtils userUtils;

    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService(userRepository, jwtService, passwordEncoder,
                        authValidation, tokenRepository, firebaseService,
                        new NotNullableMapper(), jwtUtils, userUtils);
    }

    @Test
    void signup_with_thenThrowUniqueFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@email.com";
        String uid = "uid";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setFirebaseToken("firebaseToken");

        AppFirebaseUser appFirebaseUser = new AppFirebaseUser();
        appFirebaseUser.setEmail(email);
        appFirebaseUser.setMobile(mobile);
        appFirebaseUser.setPassword(password);
        appFirebaseUser.setFirstName(firstName);
        appFirebaseUser.setLastName(lastName);

        AppFirebaseToken firebaseToken = new AppFirebaseToken();
        firebaseToken.setUid(uid);

        // when
        when(firebaseService.getToken(signupRequest.getFirebaseToken()))
                        .thenReturn(firebaseToken);
        when(firebaseService.getUser(firebaseToken.getUid())).thenReturn(appFirebaseUser);

        SignupResponse signupResponse = authService.signup(signupRequest);

        // then
        assertEquals(appFirebaseUser.getUid(), signupResponse.getUid());
        assertEquals(appFirebaseUser.getUsername(), signupResponse.getUid());
        assertEquals(signupRequest.getMobile(), signupResponse.getMobile());
        assertEquals(signupRequest.getFirstName(), signupResponse.getFirstName());
        assertEquals(signupRequest.getLastName(), signupResponse.getLastName());

        verify(firebaseService).getToken(signupRequest.getFirebaseToken());
        verify(firebaseService).getUser(firebaseToken.getUid());
        verify(userRepository).save(any());
    }

    @Test
    void login_withNotExistedUser_thenThrowEntityNotFoundException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@email.com";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSubject(mobile);
        loginRequest.setPassword(password);

        User user = new User();
        user.setEmail(email);
        user.setMobile(mobile);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // when
        when(userUtils.getUser(mobile)).thenReturn(user);
        authService.login(loginRequest);

        // then
        verify(userUtils).getUser(mobile);
        verify(authValidation).validateLogin(loginRequest);
        verify(jwtService).generateToken(user);
        verify(jwtService).generateRefreshToken(user);
    }
}
