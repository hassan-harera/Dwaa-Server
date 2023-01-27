package com.harera.hayat.core.stub.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayat.core.model.user.User;
import com.harera.hayat.core.repository.UserRepository;
import com.harera.hayat.core.stub.PasswordStubs;

@Service
public class UserStubs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordStubs passwordStubs;

    public User insert(String mobile, String rawPassword) {
        User user = create(0, mobile, rawPassword);
        return userRepository.save(user);
    }

    public User create(long id, String mobile, String rawPassword) {
        User user = new User();
        user.setId(id);
        user.setMobile(mobile);
        user.setPassword(passwordStubs.encode(rawPassword));
        return user;
    }

    public User get(String mobile) {
        return userRepository.findByMobile(mobile);
    }
}
