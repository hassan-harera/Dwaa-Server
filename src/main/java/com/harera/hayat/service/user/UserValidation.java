package com.harera.hayat.service.user;

import static com.harera.hayat.util.ErrorCode.FORMAT_USER_PASSWORD;
import static com.harera.hayat.util.ErrorCode.INCORRECT_USERNAME_FORMAT;
import static com.harera.hayat.util.ErrorCode.INCORRECT_USERNAME_OR_PASSWORD;
import static com.harera.hayat.util.ErrorCode.MANDATORY_FIRST_NAME;
import static com.harera.hayat.util.ErrorCode.MANDATORY_LAST_NAME;
import static com.harera.hayat.util.ErrorCode.MANDATORY_MOBILE;
import static com.harera.hayat.util.ErrorCode.MANDATORY_PASSWORD;
import static com.harera.hayat.util.ErrorCode.MANDATORY_TOKEN;
import static com.harera.hayat.util.ErrorCode.MANDATORY_USER_NAME;
import static com.harera.hayat.util.ErrorCode.UNIQUE_EMAIL;
import static com.harera.hayat.util.ErrorCode.UNIQUE_USER_MOBILE;
import static com.harera.hayat.util.ErrorCode.UNIQUE_USER_NAME;
import static com.harera.hayat.util.ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE;
import static com.harera.hayat.util.FieldName.AR_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.hayat.common.exception.FormatFieldException;
import com.harera.hayat.common.exception.LoginException;
import com.harera.hayat.common.exception.MandatoryFieldException;
import com.harera.hayat.common.exception.UniqueFieldException;
import com.harera.hayat.model.user.SignupRequest;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.util.StringRegexUtils;
import com.harera.hayat.util.Subject;
import com.harera.hayat.util.SubjectUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserValidation {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserValidation(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void validate(OAuthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
    }

    public void validate(LoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateLoginExisting(loginRequest);
        validatePassword(loginRequest);
    }

    public void validate(SignupRequest signupRequest) {
        validateMandatory(signupRequest);
        validateFormat(signupRequest);
        validateExisting(signupRequest);
        validatePassword(signupRequest);
    }

    private void validateFormat(SignupRequest signupRequest) {
        //format validation: username (8, 48), firstName (3, 24), lastName (3, 24), password (8, 48), email (email pattern)
        if (!StringRegexUtils.isValidUsername(signupRequest.getUsername())) {
            throw new FormatFieldException(INCORRECT_USERNAME_FORMAT,
                            MANDATORY_USER_NAME);
        }
        if (!StringRegexUtils.isValidName(signupRequest.getFirstName())) {
            throw new FormatFieldException(INCORRECT_USERNAME_FORMAT,
                            MANDATORY_FIRST_NAME);
        }
        if (!StringRegexUtils.isValidName(signupRequest.getLastName())) {
            throw new FormatFieldException(INCORRECT_USERNAME_FORMAT,
                            MANDATORY_LAST_NAME);
        }
    }

    private void validatePassword(SignupRequest signupRequest) {
        if (!StringRegexUtils.isValidPassword(signupRequest.getPassword()))
            throw new FormatFieldException(FORMAT_USER_PASSWORD, "Password",
                            signupRequest.getPassword());
    }

    private void validateLoginExisting(LoginRequest loginRequest) {
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
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validatePhoneNumberExisted(String subject) {
        if (!userRepository.existsByPhoneNumber(subject)) {
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateUsernameExisted(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateExisting(SignupRequest signupRequest) {
        validateUsernameNotExisted(signupRequest.getUsername());
        validateSubjectNotExisted(signupRequest.getSubject());
    }

    private void validateUsernameNotExisted(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UniqueFieldException(UNIQUE_USER_NAME, AR_NAME, username);
        }
    }

    private void validateSubjectNotExisted(String subject) {
        Subject subjectType = SubjectUtils.INSTANCE.getSubject(subject);
        if (subjectType instanceof Subject.Email) {
            validateEmailNotExisted(subject);
        } else if (subjectType instanceof Subject.PhoneNumber) {
            validatePhoneNumberNotExisted(subject);
        }
    }

    private void validatePhoneNumberNotExisted(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UniqueFieldException(UNIQUE_USER_MOBILE, "subject", phoneNumber);
        }
    }

    private void validateEmailNotExisted(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueFieldException(UNIQUE_EMAIL, "subject", email);
        }
    }

    private void validateMandatory(SignupRequest signupRequest) {
        //mandatory validation: mobile, firstName, lastName, password
        if (!StringUtils.hasText(signupRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_MOBILE, "mobile");
        }
        if (!StringUtils.hasText(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(MANDATORY_FIRST_NAME, "firstName");
        }
        if (!StringUtils.hasText(signupRequest.getLastName())) {
            throw new MandatoryFieldException(MANDATORY_LAST_NAME, "lastName");
        }
        if (!StringUtils.hasText(signupRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_PASSWORD, "password");
        }
    }

    private void validatePassword(LoginRequest loginRequest) {
        String subject = loginRequest.getSubject();
        if (StringRegexUtils.INSTANCE.isPhoneNumber(subject)) {
            validatePassword(loginRequest.getPassword(),
                            userRepository.findByPhoneNumber(subject).getPassword());
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
            throw new MandatoryFieldException(MANDATORY_TOKEN, "token");
        }
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingSubject(String subject) {
        if (StringRegexUtils.INSTANCE.isPhoneNumber(subject)) {
            validateLoginExistingPhoneNumber(subject);
        } else if (StringRegexUtils.INSTANCE.isEmail(subject)) {
            validateLoginExistingEmail(subject);
        } else if (StringRegexUtils.INSTANCE.isUsername(subject)) {
            validateLoginExistingUsername(subject);
        } else {
            throw new FormatFieldException(INCORRECT_USERNAME_FORMAT, "subject", null);
        }
    }

    private void validateLoginExistingUsername(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingEmail(String subject) {
        if (!userRepository.existsByEmail(subject)) {
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingPhoneNumber(String subject) {
        if (!userRepository.existsByPhoneNumber(subject)) {
            throw new LoginException(INCORRECT_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateMandatory(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getSubject())) {
            throw new MandatoryFieldException(MANDATORY_USER_NAME, "subject");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_PASSWORD, "password");
        }
    }
}
