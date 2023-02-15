package com.harera.hayat.gateway.authorization.model.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otpCode;
    private String password;
}
