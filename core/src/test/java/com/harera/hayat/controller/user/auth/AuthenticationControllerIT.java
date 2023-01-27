package com.harera.hayat.core.controller.user.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harera.hayat.core.ApplicationIT;
import com.harera.hayat.core.model.user.FirebaseUser;
import com.harera.hayat.core.model.user.User;
import com.harera.hayat.core.model.user.auth.LoginRequest;
import com.harera.hayat.core.model.user.auth.LoginResponse;
import com.harera.hayat.core.model.user.auth.SignupRequest;
import com.harera.hayat.core.model.user.auth.SignupResponse;
import com.harera.hayat.core.service.firebase.FirebaseService;
import com.harera.hayat.core.stub.PasswordStubs;
import com.harera.hayat.core.stub.user.UserStubs;
import com.harera.hayat.core.util.DataUtil;
import com.harera.hayat.core.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AuthenticationControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final UserStubs userStubs;
    private final DataUtil dataUtil;
    private final FirebaseService firebaseService;
    private final PasswordStubs passwordStubs;

    @Test
    void login_withValidRequest_thenVerifyResponse() {
        // Given
        String url = "/api/v1/login";
        String mobile = "01062227714";
        String password = "password";

        // When
        User user = userStubs.insert(mobile, password);
        ResponseEntity<LoginResponse> response = null;
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setSubject(mobile);
            loginRequest.setPassword(password);

            response = requestUtil.post(url, loginRequest, null, LoginResponse.class);
        } finally {
            dataUtil.delete(user);
        }

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
        assertNotNull(response.getBody().getRefreshToken());

        // Cleanup
        dataUtil.delete(userStubs.get(mobile));
    }

    @Test
    void signup_with_then() {
        // Given
        String url = "/api/v1/signup";
        String mobile = "01062227714";
        String password = "password";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setMobile(mobile);
        signupRequest.setPassword(password);
        signupRequest.setFirstName("Ahmed");
        signupRequest.setLastName("Mohamed");

        FirebaseUser firebaseUser = new FirebaseUser();
        firebaseUser.setMobile(mobile);
        firebaseUser.setPassword(password);
        firebaseUser.setFirstName("Ahmed");
        firebaseUser.setLastName("Mohamed");

        // When
        when(firebaseService.createUser(signupRequest)).thenReturn(firebaseUser);
        ResponseEntity<SignupResponse> response =
                        requestUtil.post(url, signupRequest, null, SignupResponse.class);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMobile());
        dataUtil.delete(userStubs.get(mobile));
    }

    @Test
    void encrypt_password() {
        // Given
        String password = passwordStubs.encode("password");
        System.out.println(password);
    }
}
