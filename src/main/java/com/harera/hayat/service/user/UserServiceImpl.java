package com.harera.hayat.service.user;

import static com.harera.hayat.util.StringRegexUtils.isEmail;
import static com.harera.hayat.util.StringRegexUtils.isPhoneNumber;
import static com.harera.hayat.util.StringRegexUtils.isUsername;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.UserRecord;
import com.harera.hayat.common.exception.SignupException;
import com.harera.hayat.model.user.User;
import com.harera.hayat.model.user.auth.LoginRequest;
import com.harera.hayat.model.user.auth.LoginResponse;
import com.harera.hayat.model.user.auth.OAuthLoginRequest;
import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.model.user.auth.SignupResponse;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.security.AuthenticationManager;
import com.harera.hayat.service.firebase.FirebaseService;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final AuthenticationManager authenticationManager;
    private final FirebaseService firebaseService;
    private final ModelMapper modelMapper;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                    UserValidation userValidation,
                    AuthenticationManager authenticationManager,
                    FirebaseService firebaseService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
        this.authenticationManager = authenticationManager;
        this.firebaseService = firebaseService;
        this.modelMapper = modelMapper;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        userValidation.validate(loginRequest);
        String uid = getUid(loginRequest.getSubject());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(authenticationManager.generateToken(uid));
        return loginResponse;
    }

    public LoginResponse login(OAuthLoginRequest oAuthLoginRequest) {
        userValidation.validate(oAuthLoginRequest);
        //TODO
        return null;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        userValidation.validate(signupRequest);
        UserRecord userRecord = firebaseService.createUser(signupRequest);
        if (userRecord == null) {
            throw new SignupException("User creation failed");
        }

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUid(userRecord.getUid());
        user.setUsername(userRecord.getUid());

        userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    private String getUid(String subject) {
        long uid = 0;
        if (isPhoneNumber(subject)) {
            uid = userRepository.findByMobile(subject).getId();
        } else if (isEmail(subject)) {
            uid = userRepository.findByEmail(subject).getId();
        } else if (isUsername(subject)) {
            uid = userRepository.findByUsername(subject).getId();
        }
        return String.valueOf(uid);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
        try {
            long userId = Integer.parseInt(username);
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
