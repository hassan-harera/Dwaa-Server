package com.harera.hayat.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.LoginException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.firebase.FirebaseService;
import com.harera.hayat.service.user.auth.AuthValidation;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class AuthValidationTest {

    @Mock
    private FirebaseService firebaseService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthValidation authValidation;

    @BeforeEach
    void setup() {
        authValidation = new AuthValidation(firebaseService, userRepository,
                        passwordEncoder);
    }

    @Test
    void validateLogin_withoutSubject_thenThrowMandatoryFieldException() {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");

        // when
        MandatoryFieldException fieldFormatException =
                        assertThrows(MandatoryFieldException.class,
                                        () -> authValidation.validateLogin(loginRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_LOGIN_SUBJECT, fieldFormatException.getCode());
    }

    @Test
    void validateLogin_withoutPassword_thenThrowMandatoryFieldException() {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSubject("subject");

        // when
        MandatoryFieldException fieldFormatException =
                        assertThrows(MandatoryFieldException.class,
                                        () -> authValidation.validateLogin(loginRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_LOGIN_PASSWORD, fieldFormatException.getCode());
    }

    @Test
    void validateLogin_withInvalidSubject_thenThrowFormatException() {
        // given
        String phoneNumber = "123456789";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSubject(phoneNumber);
        loginRequest.setPassword("password");

        // when
        FieldFormatException fieldFormatException =
                        assertThrows(FieldFormatException.class,
                                        () -> authValidation.validateLogin(loginRequest));
        // then
        assertEquals(ErrorCode.FORMAT_LOGIN_SUBJECT, fieldFormatException.getCode());
    }

    @Test
    void validateLogin_withInvalidPhoneNumber_thenThrowFormatException() {
        // given
        String phoneNumber = "123456789";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSubject(phoneNumber);
        loginRequest.setPassword("password");

        // when
        FieldFormatException fieldFormatException =
                        assertThrows(FieldFormatException.class,
                                        () -> authValidation.validateLogin(loginRequest));
        // then
        assertEquals(ErrorCode.FORMAT_LOGIN_SUBJECT, fieldFormatException.getCode());
    }

    @Test
    void validateLogin_withInvalidEmail_thenThrowFormatException() {
        // given 
        String email = "email";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSubject(email);
        loginRequest.setPassword("password");

        // when
        FieldFormatException fieldFormatException =
                        assertThrows(FieldFormatException.class,
                                        () -> authValidation.validateLogin(loginRequest));
        // then
        assertEquals(ErrorCode.FORMAT_LOGIN_SUBJECT, fieldFormatException.getCode());
    }

    @Test
    void validateLogin_withNotExistedUser_thenThrowNotFoundException() {
        // given 
        String mobile = "01062227714";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSubject(mobile);
        loginRequest.setPassword("password");

        // when
        when(userRepository.findByMobile(mobile)).thenReturn(null);

        // then
        assertThrows(LoginException.class,
                        () -> authValidation.validateLogin(loginRequest));
    }
}
