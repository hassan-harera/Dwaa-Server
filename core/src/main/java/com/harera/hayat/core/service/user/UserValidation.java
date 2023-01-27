package com.harera.hayat.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.core.exception.FieldFormatException;
import com.harera.hayat.core.exception.LoginException;
import com.harera.hayat.core.exception.MandatoryFieldException;
import com.harera.hayat.core.exception.UniqueFieldException;
import com.harera.hayat.core.model.user.auth.LoginRequest;
import com.harera.hayat.core.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.core.model.user.auth.SignupRequest;
import com.harera.hayat.core.repository.UserRepository;
import com.harera.hayat.core.service.firebase.FirebaseService;
import com.harera.hayat.core.util.ErrorCode;
import com.harera.hayat.core.util.ErrorMessage;
import com.harera.hayat.core.util.FieldName;
import com.harera.hayat.core.util.HayatStringUtils;
import com.harera.hayat.core.util.StringRegexUtils;
import com.harera.hayat.core.util.Subject;
import com.harera.hayat.core.util.SubjectUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserValidation {

    private final FirebaseService firebaseService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserValidation(FirebaseService firebaseService, UserRepository userRepository,
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

    public void validate(LoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateExisting(loginRequest);
        validatePassword(loginRequest);
    }

    public void validate(OAuthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
        FirebaseToken firebaseToken =
                        firebaseService.verifyToken(loginRequest.getDeviceToken());
        validateExistingEmail(firebaseToken.getEmail());
        validateExistedUid(firebaseToken.getUid());
    }

    private void validateExistedUid(String uid) {
        if (!userRepository.existsByUid(uid)) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN,
                            "Invalid firebase token");
        }
    }

    private void validateFormat(SignupRequest signupRequest) {
        //format validation: firstName (3, 24), lastName (3, 24), password (6, 68), email (email pattern)
        if (!HayatStringUtils.isValidMobile(signupRequest.getMobile())) {
            throw new FieldFormatException(ErrorCode.FORMAT_USER_MOBILE,
                            "Incorrect mobile format");
        }
        if (!HayatStringUtils.isValidName(signupRequest.getFirstName())) {
            throw new FieldFormatException(ErrorCode.FORMAT_FIRST_NAME,
                            "Incorrect first name format");
        }
        if (!HayatStringUtils.isValidName(signupRequest.getLastName())) {
            throw new FieldFormatException(ErrorCode.FORMAT_LAST_NAME,
                            ErrorCode.MANDATORY_LAST_NAME);
        }
        if (!HayatStringUtils.isValidPassword(signupRequest.getPassword())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_PASSWORD,
                            ErrorCode.MANDATORY_LAST_NAME);
        }
        if (signupRequest.getEmail() != null
                        && !HayatStringUtils.isValidEmail(signupRequest.getEmail())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_EMAIL,
                            ErrorCode.MANDATORY_LAST_NAME);
        }
    }

    private void validateExisting(LoginRequest loginRequest) {
        validateSubjectExisted(loginRequest.getSubject());
    }

    private void validateSubjectExisted(String subject) {
        Subject subjectType = SubjectUtils.INSTANCE.getSubject(subject);
        if (subjectType instanceof Subject.Email) {
            validateEmailExisted(subject);
        } else if (subjectType instanceof Subject.PhoneNumber) {
            validatePhoneNumberExisted(subject);
        } else if (subjectType instanceof Subject.Username) {
            validateUsernameExisted(subject);
        }
    }

    private void validateEmailExisted(String subject) {
        if (!userRepository.existsByEmail(subject)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validatePhoneNumberExisted(String subject) {
        if (!userRepository.existsByMobile(subject)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateUsernameExisted(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateExisting(SignupRequest signupRequest) {
        validateMobileNotExisted(signupRequest.getMobile());
        if (signupRequest.getEmail() != null) {
            validateEmailNotExisted(signupRequest.getEmail());
        }
    }

    private void validateUsernameNotExisted(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UniqueFieldException(ErrorCode.UNIQUE_USER_NAME, FieldName.AR_NAME,
                            username);
        }
    }

    private void validateMobileNotExisted(String phoneNumber) {
        if (userRepository.existsByMobile(phoneNumber)) {
            throw new UniqueFieldException(ErrorCode.UNIQUE_USER_MOBILE, "subject",
                            phoneNumber);
        }
    }

    private void validateEmailNotExisted(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueFieldException(ErrorCode.UNIQUE_EMAIL, "subject", email);
        }
    }

    private void validateMandatory(SignupRequest signupRequest) {
        //mandatory validation: mobile, firstName, lastName, password
        if (!StringUtils.hasText(signupRequest.getMobile())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_USER_MOBILE, "mobile");
        }
        if (!StringUtils.hasText(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_FIRST_NAME,
                            "firstName");
        }
        if (!StringUtils.hasText(signupRequest.getLastName())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_LAST_NAME, "lastName");
        }
        if (!StringUtils.hasText(signupRequest.getPassword())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_PASSWORD, "password");
        }
    }

    private void validatePassword(LoginRequest loginRequest) {
        String subject = loginRequest.getSubject();
        if (StringRegexUtils.INSTANCE.isPhoneNumber(subject)) {
            validatePassword(loginRequest.getPassword(),
                            userRepository.findByMobile(subject).getPassword());
        } else if (StringRegexUtils.INSTANCE.isEmail(subject)) {
            validatePassword(loginRequest.getPassword(),
                            userRepository.findByEmail(subject).getPassword());
        } else {
            validatePassword(loginRequest.getPassword(),
                            userRepository.findByUsername(subject).getPassword());
        }
    }

    private void validateMandatory(OAuthLoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getToken())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_TOKEN, "token");
        }
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingSubject(String subject) {
        if (StringRegexUtils.INSTANCE.isPhoneNumber(subject)) {
            validateLoginExistingPhoneNumber(subject);
        } else if (StringRegexUtils.INSTANCE.isEmail(subject)) {
            validateExistingEmail(subject);
        } else if (StringRegexUtils.INSTANCE.isUsername(subject)) {
            validateLoginExistingUsername(subject);
        } else {
            throw new FieldFormatException(ErrorCode.INCORRECT_USERNAME_FORMAT, "subject",
                            null);
        }
    }

    private void validateLoginExistingUsername(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateExistingEmail(String subject) {
        if (!userRepository.existsByEmail(subject)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingPhoneNumber(String subject) {
        if (!userRepository.existsByMobile(subject)) {
            throw new LoginException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD,
                            ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateMandatory(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getSubject())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_USER_NAME, "subject");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_PASSWORD, "password");
        }
    }
}
