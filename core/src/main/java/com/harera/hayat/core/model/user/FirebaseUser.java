package com.harera.hayat.core.model.user;

import lombok.Data;

@Data
public class FirebaseUser extends com.harera.hayat.core.model.user.UserDto {

    private String uid;
    private String email;
    private String phoneNumber;
    private String displayName;
    private String photoUrl;
}
