package com.harera.hayat.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.UniqueFieldException;
import com.harera.hayat.model.user.FirebaseUser;
import com.harera.hayat.model.user.User;
import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.model.user.auth.SignupResponse;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.repository.user.auth.TokenRepository;
import com.harera.hayat.service.firebase.FirebaseService;
import com.harera.hayat.service.user.auth.AuthService;
import com.harera.hayat.service.user.auth.AuthValidation;
import com.harera.hayat.service.user.auth.JwtService;
import com.harera.hayat.service.user.auth.JwtUtils;
import com.harera.hayat.util.ErrorCode;

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

    private ModelMapper modelMapper = new ModelMapper();
    private AuthValidation authValidation;
    private AuthService authService;

    @BeforeEach
    void setup() {
        authValidation = new AuthValidation(firebaseService, userRepository,
                        passwordEncoder);
        authService = new AuthService(userRepository, jwtService, passwordEncoder,
                        authValidation, tokenRepository, firebaseService, modelMapper,
                        jwtUtils);
    }

    @Test
    void signup_withoutMobile_thenThrowMandatoryFieldException() {
        // given
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
    }

    @Test
    void signup_withoutPassword_thenThrowMandatoryFieldException() {
        // given
        String mobile = "mobile";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
    }

    @Test
    void signup_withoutFirstname_thenThrowMandatoryFieldException() {
        // given
        String mobile = "mobile";
        String password = "password";
        String lastName = "lastName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
    }

    @Test
    void signup_withoutLastname_thenThrowMandatoryFieldException() {
        // given
        String mobile = "mobile";
        String password = "password";
        String firstName = "firstName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
    }

    @Test
    void signup_withInvalidMobile_thenThrowFormatFieldException() {
        // given
        String mobile = "01062227";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
        assertTrue(ex instanceof FieldFormatException);
        assertEquals(ErrorCode.FORMAT_USER_MOBILE, ((FieldFormatException) ex).getCode());
    }

    @Test
    void signup_withInvalidFirstname_thenThrowFormatFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "f";
        String lastName = "lastName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
        assertTrue(ex instanceof FieldFormatException);
        assertEquals(ErrorCode.FORMAT_FIRST_NAME, ((FieldFormatException) ex).getCode());
    }

    @Test
    void signup_withInvalidLastName_thenThrowFormatFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "l";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
        assertTrue(ex instanceof FieldFormatException);
        assertEquals(ErrorCode.FORMAT_LAST_NAME, ((FieldFormatException) ex).getCode());
    }

    @Test
    void signup_withInvalidEmail_thenThrowFormatFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
        assertTrue(ex instanceof FieldFormatException);
        assertEquals(ErrorCode.FORMAT_SIGNUP_EMAIL,
                        ((FieldFormatException) ex).getCode());
    }

    @Test
    void signup_withUnuniqueMobile_thenThrowUniqueFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@email.com";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        when(userRepository.existsByMobile(signupRequest.getMobile())).thenReturn(true);
        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNotNull(ex);
        assertTrue(ex instanceof UniqueFieldException);
        assertEquals(ErrorCode.UNIQUE_USER_MOBILE, ((UniqueFieldException) ex).getCode());
    }

    @Test
    void signup_with_thenThrowUniqueFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@email.com";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        FirebaseUser firebaseUser = new FirebaseUser();
        firebaseUser.setEmail(email);
        firebaseUser.setMobile(mobile);
        firebaseUser.setPassword(password);
        firebaseUser.setFirstName(firstName);
        firebaseUser.setLastName(lastName);

        User user = new User();
        user.setEmail(email);
        user.setMobile(mobile);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        SignupResponse signupResponse = new SignupResponse();
        signupResponse.setMobile(mobile);

        // when
        Exception ex = null;
        when(firebaseService.createUser(signupRequest)).thenReturn(firebaseUser);

        try {
            authService.signup(signupRequest);
        } catch (Exception e) {
            ex = e;
        }

        // then
        assertNull(ex);
        assertNotNull(signupResponse);
    }
}
