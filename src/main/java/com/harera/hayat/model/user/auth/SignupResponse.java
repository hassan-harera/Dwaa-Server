package com.harera.hayat.model.user.auth;

import lombok.Data;

@Data
public class SignupResponse {
    private long id;
    private String uid;
    private String username;
    private String mobile;
    private String email;
    private String profileImage;
    private String coverImage;
    private String bio;
    private String token;
    private String deviceToken;
    private String refreshToken;
    private String role;
    private String firstName;
    private String lastName;
}
