package com.harera.hayat.service.firebase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.harera.hayat.common.exception.LoginException;
import com.harera.hayat.model.user.auth.SignupRequest;
import com.harera.hayat.util.ErrorCode;

import kotlin.jvm.internal.Intrinsics;

@Service
public final class FirebaseService {

    private final FirebaseAuth firebaseAuth;

    public FirebaseService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
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
    public UserRecord createUser(@NotNull SignupRequest signupRequest) {
        Intrinsics.checkNotNullParameter(signupRequest, "signupRequest");
        UserRecord.CreateRequest userRecord = (new UserRecord.CreateRequest())
                        .setPhoneNumber(signupRequest.getMobile())
                        .setEmail(signupRequest.getEmail()).setEmailVerified(false)
                        .setPassword(signupRequest.getPassword())
                        .setDisplayName(signupRequest.getFirstName() + " "
                                        + signupRequest.getLastName())
                        .setPassword(signupRequest.getPassword()).setDisabled(false);

        try {
            return this.firebaseAuth.createUser(userRecord);
        } catch (FirebaseAuthException e) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN, "Invalid token");
        }
    }
}
