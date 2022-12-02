package com.harera.hayatserver.service.user;

import static com.harera.hayatserver.util.StringRegexUtils.isEmail;
import static com.harera.hayatserver.util.StringRegexUtils.isPhoneNumber;
import static com.harera.hayatserver.util.StringRegexUtils.isUsername;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.model.user.LoginRequest;
import com.harera.hayatserver.model.user.LoginResponse;
import com.harera.hayatserver.model.user.OAuthLoginRequest;
import com.harera.hayatserver.model.user.SignupRequest;
import com.harera.hayatserver.model.user.SignupResponse;
import com.harera.hayatserver.model.user.User;
import com.harera.hayatserver.repository.UserRepository;
import com.harera.hayatserver.service.firebase.FirebaseService;
import com.harera.security.AuthenticationManager;

import lombok.val;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final AuthenticationManager authenticationManager;
    private final FirebaseService firebaseService;
    private final ModelMapper modelMapper;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserValidation userValidation, AuthenticationManager authenticationManager, FirebaseService firebaseService, ModelMapper modelMapper) {
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
        return new LoginResponse(authenticationManager.generateToken(uid));
    }

    public LoginResponse login(OAuthLoginRequest oAuthLoginRequest) {
        userValidation.validate(oAuthLoginRequest);
        //TODO
        return null;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        userValidation.validate(signupRequest);
        val firebaseToken = firebaseService.verifyToken(signupRequest.getToken());
        val firebaseUser = firebaseService.getUser(firebaseToken);
        val user = modelMapper.map(firebaseUser, User.class);
//        val user = User(
//            username = firebaseUser.uid,
//            firstName = signupRequest.firstName!!,
//            lastName = signupRequest.lastName!!,
//            password = passwordEncoder.encode(signupRequest.password!!),
//            email = firebaseUser.email,
//            phoneNumber = firebaseUser.phoneNumber
//        )
        userRepository.save(user);
        return new SignupResponse(authenticationManager.generateToken(firebaseUser.getUid()));
    }

    private String getUid(String subject) {
        long uid = 0;
        if (isPhoneNumber(subject)) {
            uid = userRepository.findByPhoneNumber(subject).getId();
        } else if (isEmail(subject)) {
            uid = userRepository.findByEmail(subject).getId();
        } else if (isUsername(subject)) {
            uid = userRepository.findByUsername(subject).getId();
        }
        return String.valueOf(uid);
    }
}