package com.harera.dwaaserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class LoginException extends RuntimeException {

    private String code;

    public LoginException(String errorCode, String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "LogicError {" +
                "code='" + code + '\'' +
                ", message='" + getMessage() + '\'';
    }
}
