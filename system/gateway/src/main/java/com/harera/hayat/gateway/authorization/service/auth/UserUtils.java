package com.harera.hayat.common.service.auth;

import static com.harera.hayat.common.util.ErrorCode.NOT_FOUND_USER;
import static com.harera.hayat.common.util.RegexUtils.isEmail;
import static com.harera.hayat.common.util.RegexUtils.isPhoneNumber;
import static com.harera.hayat.common.util.RegexUtils.isUsername;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.harera.hayat.common.exception.EntityNotFoundException;
import com.harera.hayat.common.model.user.User;
import com.harera.hayat.common.repository.user.UserRepository;

@Service
public class UserUtils {

    private final UserRepository userRepository;

    public UserUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String subject) {
        Optional<User> user = Optional.empty();
        if (isPhoneNumber(subject)) {
            user = userRepository.findByMobile(subject);
        } else if (isEmail(subject)) {
            user = userRepository.findByEmail(subject);
        } else if (isUsername(subject)) {
            user = userRepository.findByUsername(subject);
        }

        return user.orElseThrow(
                        () -> new EntityNotFoundException(User.class, 0, NOT_FOUND_USER));
    }
}
