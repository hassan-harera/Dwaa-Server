package com.harera.hayat.gateway.authorization.model.user;

import lombok.Data;

@Data
public class AppFirebaseUser extends UserDto {

    private String uid;
    private String email;
    private String phoneNumber;
    private String displayName;
    private String photoUrl;
}
