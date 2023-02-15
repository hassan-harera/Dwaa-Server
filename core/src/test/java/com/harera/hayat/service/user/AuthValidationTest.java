package com.harera.hayat.service.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.harera.hayat.common.repository.user.UserRepository;
import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.LoginException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.exception.UniqueFieldException;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.model.user.auth.SignupRequest;
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
        String phoneNumber = "aa";
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

        // then
        assertThrows(LoginException.class,
                        () -> authValidation.validateLogin(loginRequest));
    }

    @Test
    void validateOauthLogin_withoutOauthToken_thenThrowMandatoryFieldException() {
        // given
        OAuthLoginRequest loginRequest = new OAuthLoginRequest();
        loginRequest.setDeviceToken("google");

        // when
        MandatoryFieldException fieldFormatException =
                        assertThrows(MandatoryFieldException.class,
                                        () -> authValidation.validate(loginRequest));
        // then
        assertEquals(ErrorCode.MANDATORY_LOGIN_OAUTH_TOKEN,
                        fieldFormatException.getCode());
    }

    @Test
    void signup_withoutFirebaseToken_thenThrowMandatoryFieldException() {
        // given
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setMobile("01062227714");
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class,
                        () -> authValidation.validate(signupRequest));

        // then
        assertNotNull(ex);
        assertEquals(ErrorCode.MANDATORY_SIGNUP_FIREBASE_TOKEN, ex.getCode());
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
            authValidation.validate(signupRequest);
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
            authValidation.validate(signupRequest);
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
        MandatoryFieldException ex = assertThrows(MandatoryFieldException.class,
                        () -> authValidation.validate(signupRequest));

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
            authValidation.validate(signupRequest);
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
        signupRequest.setFirebaseToken("firebaseToken");
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authValidation.validate(signupRequest);
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
        signupRequest.setFirebaseToken("firebaseToken");
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authValidation.validate(signupRequest);
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
        signupRequest.setFirebaseToken("firebaseToken");
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authValidation.validate(signupRequest);
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
        signupRequest.setFirebaseToken("firebaseToken");
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        Exception ex = null;
        try {
            authValidation.validate(signupRequest);
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
    void signup_withNotUniqueMobile_thenThrowUniqueFieldException() {
        // given
        String mobile = "01062227714";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@email.com";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirebaseToken("firebaseToken");
        signupRequest.setEmail(email);
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);

        // when
        when(userRepository.existsByMobile(signupRequest.getMobile())).thenReturn(true);

        UniqueFieldException ex = assertThrows(UniqueFieldException.class,
                        () -> authValidation.validate(signupRequest));

        // then
        assertEquals(ErrorCode.UNIQUE_USER_MOBILE, ex.getCode());
    }
}
