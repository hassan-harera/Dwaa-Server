package com.harera.hayat.core.service.firebase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.harera.hayat.core.exception.LoginException;
import com.harera.hayat.core.model.user.FirebaseUser;
import com.harera.hayat.core.model.user.auth.SignupRequest;
import com.harera.hayat.core.util.ErrorCode;

import kotlin.jvm.internal.Intrinsics;

@Service
public class FirebaseService {

    private final FirebaseAuth firebaseAuth;
    private final ModelMapper modelMapper;

    public FirebaseService(FirebaseAuth firebaseAuth, ModelMapper modelMapper) {
        this.firebaseAuth = firebaseAuth;
        this.modelMapper = modelMapper;
    }

    @NotNull
    public FirebaseToken verifyToken(String token) {
        try {
            return this.firebaseAuth.verifyIdToken(token, true);
        } catch (FirebaseAuthException e) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN, "Invalid token");
        }
    }

    @NotNull
    public UserRecord getUser(FirebaseToken firebaseToken) {
        try {
            return firebaseAuth.getUser(
                            firebaseToken != null ? firebaseToken.getUid() : null);
        } catch (FirebaseAuthException e) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN, "Invalid token");
        }
    }

    @Nullable
    public FirebaseUser createUser(@NotNull SignupRequest signupRequest) {
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
                            FirebaseUser.class);
        } catch (FirebaseAuthException e) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN, "Invalid token");
        }
    }
}
