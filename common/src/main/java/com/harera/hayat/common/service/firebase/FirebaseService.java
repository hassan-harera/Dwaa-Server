package com.harera.hayat.common.service.firebase;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.harera.hayat.common.exception.InvalidTokenException;
import com.harera.hayat.common.exception.LoginException;
import com.harera.hayat.common.model.user.AppFirebaseToken;
import com.harera.hayat.common.model.user.AppFirebaseUser;
import com.harera.hayat.common.model.user.auth.SignupRequest;
import com.harera.hayat.common.util.ErrorCode;

import kotlin.jvm.internal.Intrinsics;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FirebaseService {

    private final FirebaseAuth firebaseAuth;
    private final ModelMapper modelMapper;

    public FirebaseService(FirebaseAuth firebaseAuth, ModelMapper modelMapper) {
        this.firebaseAuth = firebaseAuth;
        this.modelMapper = modelMapper;
    }

    public AppFirebaseToken getToken(String token) {
        if (isBlank(token))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                            "Invalid Token");
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token, true);
            if (firebaseToken == null)
                throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                                "Invalid Token");
            return modelMapper.map(firebaseToken, AppFirebaseToken.class);
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public AppFirebaseUser getUser(String uid) {
        if (isBlank(uid))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_UID,
                            "Invalid uid");
        try {
            UserRecord user = firebaseAuth.getUser(uid);
            if (user == null)
                throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                                "Invalid Token");

            return modelMapper.map(user, AppFirebaseUser.class);
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public AppFirebaseUser createUser(@NotNull SignupRequest signupRequest) {
        Intrinsics.checkNotNullParameter(signupRequest, "signupRequest");
        UserRecord.CreateRequest userRecord = (new UserRecord.CreateRequest())
                        .setPhoneNumber("+2" + signupRequest.getMobile())
                        .setPassword(signupRequest.getPassword())
                        .setDisplayName(signupRequest.getFirstName() + " "
                                        + signupRequest.getLastName())
                        .setPassword(signupRequest.getPassword()).setDisabled(false);

        if (signupRequest.getEmail() != null) {
            userRecord.setEmail(signupRequest.getEmail());
        }

        try {
            return modelMapper.map(firebaseAuth.createUser(userRecord),
                            AppFirebaseUser.class);
        } catch (FirebaseAuthException e) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN, "Invalid token");
        }
    }

    public String generateToken(String uid) {
        if (isBlank(uid))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_UID,
                            "Invalid uid");
        try {
            return firebaseAuth.createCustomToken(uid);
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
