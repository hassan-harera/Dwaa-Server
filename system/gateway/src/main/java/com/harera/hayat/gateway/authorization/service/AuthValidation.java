package com.harera.hayat.gateway.authorization.service;

import static com.harera.hayat.gateway.authorization.util.ErrorCode.FORMAT_LOGIN_SUBJECT;
import static org.springframework.util.StringUtils.hasText;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harera.hayat.gateway.authorization.exception.FieldFormatException;
import com.harera.hayat.gateway.authorization.exception.MandatoryFieldException;
import com.harera.hayat.gateway.authorization.exception.UniqueFieldException;
import com.harera.hayat.gateway.authorization.model.auth.LoginRequest;
import com.harera.hayat.gateway.authorization.model.auth.OAuthLoginRequest;
import com.harera.hayat.gateway.authorization.model.auth.SignupRequest;
import com.harera.hayat.gateway.authorization.model.user.AppFirebaseToken;
import com.harera.hayat.gateway.authorization.model.user.AppFirebaseUser;
import com.harera.hayat.gateway.authorization.repository.user.UserRepository;
import com.harera.hayat.gateway.authorization.util.ErrorCode;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthValidation {

    private final FirebaseService firebaseService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthValidation(FirebaseService firebaseService, UserRepository userRepository,
                    PasswordEncoder encoder) {
        this.firebaseService = firebaseService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void validate(SignupRequest signupRequest) {
        validateMandatory(signupRequest);
        validateFormat(signupRequest);
        validateExisting(signupRequest);
    }

    public void validateLogin(LoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateFormat(loginRequest);
        validateExisting(loginRequest);
        validatePassword(loginRequest);
    }

    private void validateFormat(LoginRequest loginRequest) {
        FieldFormatException incorrectUsernameFormat = new FieldFormatException(
                        FORMAT_LOGIN_SUBJECT, "subject", loginRequest.getSubject());

        String subjectPayload = loginRequest.getSubject();
        Subject subjectType = getSubject(subjectPayload);

        if (!(subjectType instanceof Subject.Email)
                        && !(subjectType instanceof Subject.PhoneNumber)) {
            throw incorrectUsernameFormat;
        }
    }

    public void validate(OAuthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
        AppFirebaseToken firebaseToken =
                        firebaseService.getToken(loginRequest.getDeviceToken());
        validateUidExisted(firebaseToken.getUid());
    }

    private void validateUidExisted(String uid) {
        if (!userRepository.existsByUid(uid)) {
            throw new LoginException(INVALID_FIREBASE_TOKEN, "Invalid firebase token");
        }
    }

    private void validateFormat(SignupRequest signupRequest) {
        //format validation: firstName (3, 24), lastName (3, 24), password (6, 68), email (email pattern)
        if (!HayatStringUtils.isValidMobile(signupRequest.getMobile())) {
            throw new FieldFormatException(FORMAT_USER_MOBILE, "Incorrect mobile format");
        }
        if (!HayatStringUtils.isValidName(signupRequest.getFirstName())) {
            throw new FieldFormatException(FORMAT_FIRST_NAME,
                            "Incorrect first name format");
        }
        if (!HayatStringUtils.isValidName(signupRequest.getLastName())) {
            throw new FieldFormatException(FORMAT_LAST_NAME, MANDATORY_LAST_NAME);
        }
        if (!HayatStringUtils.isValidPassword(signupRequest.getPassword())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_PASSWORD,
                            MANDATORY_LAST_NAME);
        }
        if (signupRequest.getEmail() != null && !isValidEmail(signupRequest.getEmail())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_EMAIL,
                            MANDATORY_LAST_NAME);
        }
    }

    private void validateExisting(LoginRequest loginRequest) {
        validateSubjectExisted(loginRequest.getSubject());
    }

    private void validateSubjectExisted(String subject) {
        Subject subjectType = getSubject(subject);
        if (subjectType instanceof Subject.Email) {
            validateEmailExisted(subject);
        } else if (subjectType instanceof Subject.PhoneNumber) {
            validatePhoneNumberExisted(subject);
        } else {
            validateUsernameExisted(subject);
        }
    }

    private void validatePhoneNumberExisted(String subject) {
        if (!userRepository.existsByMobile(subject)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateUsernameExisted(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateExisting(SignupRequest signupRequest) {
        validateMobileNotExisted(signupRequest.getMobile());
        if (signupRequest.getEmail() != null) {
            validateEmailNotExisted(signupRequest.getEmail());
        }
    }

    private void validateMobileNotExisted(String phoneNumber) {
        if (userRepository.existsByMobile(phoneNumber)) {
            throw new UniqueFieldException(UNIQUE_USER_MOBILE, "subject", phoneNumber);
        }
    }

    private void validateEmailNotExisted(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueFieldException(UNIQUE_EMAIL, "subject", email);
        }
    }

    private void validateMandatory(SignupRequest signupRequest) {
        if (!hasText(signupRequest.getFirebaseToken())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_FIREBASE_TOKEN, "mobile");
        }
        if (!hasText(signupRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_USER_MOBILE, "mobile");
        }
        if (!hasText(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(MANDATORY_FIRST_NAME, "firstName");
        }
        if (!hasText(signupRequest.getLastName())) {
            throw new MandatoryFieldException(MANDATORY_LAST_NAME, "lastName");
        }
        if (!hasText(signupRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_PASSWORD, "password");
        }
    }

    private void validatePassword(LoginRequest loginRequest) {
        String subjectPayload = loginRequest.getSubject();
        Subject subjectType = getSubject(subjectPayload);
        Optional<User> user;
        if (subjectType instanceof Subject.PhoneNumber) {
            user = userRepository.findByMobile(subjectPayload);
        } else if (subjectType instanceof Subject.Email) {
            user = userRepository.findByEmail(subjectPayload);
        } else {
            user = userRepository.findByUsername(subjectPayload);
        }
        user.ifPresent(u -> validatePassword(loginRequest.getPassword(),
                        u.getPassword()));
    }

    private void validateMandatory(OAuthLoginRequest loginRequest) {
        if (isBlank(loginRequest.getFirebaseToken())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_OAUTH_TOKEN, "oauth_token");
        }
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateEmailExisted(String subject) {
        if (!userRepository.existsByEmail(subject)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateMandatory(LoginRequest loginRequest) {
        if (!hasText(loginRequest.getSubject())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_SUBJECT, "subject");
        }

        if (!hasText(loginRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_PASSWORD, "password");
        }
    }

    public void validate(AppFirebaseUser firebaseUser, SignupRequest signupRequest) {
        validateExisting(firebaseUser);
        validateIdentical(firebaseUser, signupRequest);
    }

    private void validateExisting(AppFirebaseUser firebaseUser) {
        if (userRepository.existsByUid(firebaseUser.getUid())) {
            throw new UniqueFieldException(ErrorCode.UNIQUE_SIGNUP_UID, "firebase_uid",
                            firebaseUser.getUid());
        }
        if (hasText(firebaseUser.getMobile())
                        && userRepository.existsByMobile(firebaseUser.getMobile())) {
            throw new UniqueFieldException(UNIQUE_SIGNUP_MOBILE, "firebase_uid",
                            firebaseUser.getUid());
        }
        if (hasText(firebaseUser.getEmail())
                        && userRepository.existsByEmail(firebaseUser.getEmail())) {
            throw new UniqueFieldException(UNIQUE_SIGNUP_EMAIL, "firebase_uid",
                            firebaseUser.getUid());
        }
    }

    private void validateIdentical(AppFirebaseUser firebaseUser,
                    SignupRequest signupRequest) {
        if (hasText(firebaseUser.getMobile())
                        && !firebaseUser.getMobile().equals(signupRequest.getMobile())) {
            throw new FieldFormatException(ErrorCode.DIFFERENT_SIGNUP_MOBILE,
                            MANDATORY_LAST_NAME);
        }
        if (hasText(firebaseUser.getEmail())
                        && !firebaseUser.getEmail().equals(signupRequest.getEmail())) {
            throw new FieldFormatException(ErrorCode.DIFFERENT_SIGNUP_MOBILE,
                            MANDATORY_LAST_NAME);
        }
    }
}
