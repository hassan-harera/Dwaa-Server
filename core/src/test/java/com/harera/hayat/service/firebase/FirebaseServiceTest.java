package com.harera.hayat.service.firebase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.harera.hayat.exception.InvalidTokenException;
import com.harera.hayat.util.ErrorCode;

@ExtendWith(MockitoExtension.class)
class FirebaseServiceTest {

    @Mock
    private FirebaseAuth firebaseAuth;
    @Mock
    private ModelMapper modelMapper;

    private FirebaseService firebaseService;

    @BeforeEach
    void setup() {
        firebaseService = new FirebaseService(firebaseAuth, modelMapper);
    }

    @Test
    void getFirebaseToken_withEmptyToken_thenThrowInvalidTokenException() {
        // given
        String token = "";

        // when
        InvalidTokenException invalidTokenException =
                        assertThrows(InvalidTokenException.class,
                                        () -> firebaseService.getToken(token));
        // then
        assertEquals(ErrorCode.INVALID_FIREBASE_TOKEN, invalidTokenException.getCode());
    }

    @Test
    void getFirebaseToken_withInvalidToken_thenThrowInvalidTokenException()
                    throws FirebaseAuthException {
        // given
        String token = "token";

        // when
        when(firebaseAuth.verifyIdToken(token, true)).thenReturn(null);
        InvalidTokenException invalidTokenException =
                        assertThrows(InvalidTokenException.class,
                                        () -> firebaseService.getToken(token));
        // then
        assertEquals(ErrorCode.INVALID_FIREBASE_TOKEN, invalidTokenException.getCode());
    }

    @Test
    void getFirebaseToken_withFailedFirebaseCall_thenThrowInvalidTokenException()
                    throws FirebaseAuthException {
        // given
        String token = "token";

        // when
        when(firebaseAuth.verifyIdToken(token, true))
                        .thenThrow(FirebaseAuthException.class);
        assertThrows(RuntimeException.class,
                        () -> firebaseService.getToken(token));
    }

    @Test
    void getUser_withEmptyUid_thenThrowInvalidTokenException() {
        // given
        String uid = "";

        // when
        InvalidTokenException invalidTokenException = assertThrows(
                        InvalidTokenException.class, () -> firebaseService.getUser(uid));

        // then
        assertEquals(ErrorCode.INVALID_FIREBASE_UID, invalidTokenException.getCode());
    }

    @Test
    void getUser_withNullValueReturnFromFirebaseAuth_thenThrowInvalidTokenException()
                    throws FirebaseAuthException {
        // given
        String uid = "uid";

        // when
        when(firebaseAuth.getUser(uid)).thenReturn(null);
        InvalidTokenException invalidTokenException = assertThrows(
                        InvalidTokenException.class, () -> firebaseService.getUser(uid));
        // then
        assertEquals(ErrorCode.INVALID_FIREBASE_TOKEN, invalidTokenException.getCode());
    }

    @Test
    void getUser_withFailedFirebaseCall_thenThrowInvalidTokenException() {
        // given
        String uid = "uid";

        // when
        assertThrows(RuntimeException.class, () -> firebaseService.getUser(uid));
    }
}
