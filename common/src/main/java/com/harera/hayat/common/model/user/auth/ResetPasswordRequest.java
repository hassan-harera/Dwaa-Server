package com.harera.hayat.common.model.user.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otpCode;
    private String password;
}
