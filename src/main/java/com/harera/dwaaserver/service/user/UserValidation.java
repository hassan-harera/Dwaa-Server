package com.harera.dwaaserver.service.user;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.harera.dwaaserver.exception.FormatFieldException;
import com.harera.dwaaserver.exception.LoginException;
import com.harera.dwaaserver.exception.MandatoryFieldException;
import com.harera.dwaaserver.model.user.LoginRequest;
import com.harera.dwaaserver.model.user.OAuthLoginRequest;
import com.harera.dwaaserver.repository.UserRepository;
import com.harera.dwaaserver.util.MStringUtils;

import static com.harera.dwaaserver.util.ErrorCode.INCORRECT_USERNAME_FORMAT;
import static com.harera.dwaaserver.util.ErrorCode.INCORRECT_USERNAME_PASSWORD_CODE;
import static com.harera.dwaaserver.util.ErrorCode.MANDATORY_PASSWORD;
import static com.harera.dwaaserver.util.ErrorCode.MANDATORY_TOKEN;
import static com.harera.dwaaserver.util.ErrorCode.MANDATORY_USER_NAME;
import static com.harera.dwaaserver.util.ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE;

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

    public void validateLogin(OAuthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
    }

    public void validateLogin(LoginRequest loginRequest) {
        validateLoginMandatory(loginRequest);
        validateLoginExistingSubject(loginRequest.getSubject());
        validatePassword(loginRequest);
    }

    private void validatePassword(LoginRequest loginRequest) {
        String subject = loginRequest.getSubject();
        if (MStringUtils.INSTANCE.isPhoneNumber(subject)) {
            validatePassword(loginRequest.getPassword(), userRepository.findByPhoneNumber(subject).getPassword());
        } else if (MStringUtils.INSTANCE.isEmail(subject)) {
            validatePassword(loginRequest.getPassword(), userRepository.findByEmail(subject).getPassword());
        } else {
            validatePassword(loginRequest.getPassword(), userRepository.findByUsername(subject).getPassword());
        }
    }

    private void validateMandatory(OAuthLoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getToken())) {
            throw new MandatoryFieldException(MANDATORY_TOKEN, "token");
        }
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new LoginException(INCORRECT_USERNAME_PASSWORD_CODE, INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingSubject(String subject) {
        if (MStringUtils.INSTANCE.isPhoneNumber(subject)) {
            validateLoginExistingPhoneNumber(subject);
        } else if (MStringUtils.INSTANCE.isEmail(subject)) {
            validateLoginExistingEmail(subject);
        } else if (MStringUtils.INSTANCE.isUsername(subject)) {
            validateLoginExistingUsername(subject);
        } else {
            throw new FormatFieldException(INCORRECT_USERNAME_FORMAT, "subject", null);
        }
    }

    private void validateLoginExistingUsername(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(INCORRECT_USERNAME_PASSWORD_CODE, INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingEmail(String subject) {
        if (!userRepository.existsByEmail(subject)) {
            throw new LoginException(INCORRECT_USERNAME_PASSWORD_CODE, INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginExistingPhoneNumber(String subject) {
        if (!userRepository.existsByPhoneNumber(subject)) {
            throw new LoginException(INCORRECT_USERNAME_PASSWORD_CODE, INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateLoginMandatory(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getSubject())) {
            throw new MandatoryFieldException(MANDATORY_USER_NAME, "subject");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_PASSWORD, "password");
        }
    }

    public String getUsername(String subject) {
        if (MStringUtils.INSTANCE.isPhoneNumber(subject)) {
            return userRepository.findByPhoneNumber(subject).getUsername();
        } else if (MStringUtils.INSTANCE.isEmail(subject)) {
            return userRepository.findByEmail(subject).getUsername();
        } else if (MStringUtils.INSTANCE.isUsername(subject)) {
            return userRepository.findByUsername(subject).getUsername();
        } else {
            throw new FormatFieldException(INCORRECT_USERNAME_FORMAT, "subject", null);
        }
    }
}
