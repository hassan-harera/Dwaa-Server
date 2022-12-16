package com.harera.hayat.model.user;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otpCode;
    private String password;
}
