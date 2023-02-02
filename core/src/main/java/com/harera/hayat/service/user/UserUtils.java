package com.harera.hayat.service.user;

import static com.harera.hayat.util.RegexUtils.isEmail;
import static com.harera.hayat.util.RegexUtils.isPhoneNumber;
import static com.harera.hayat.util.RegexUtils.isUsername;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.util.ErrorCode;

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

        return user.orElseThrow(() -> new EntityNotFoundException(User.class, 0,
                        ErrorCode.NOT_FOUND_USER));
    }
}
