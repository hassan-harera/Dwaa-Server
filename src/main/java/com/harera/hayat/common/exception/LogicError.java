package com.harera.hayat.common.exception;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LogicError extends RuntimeException {
    private String code;

    public LogicError(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "LogicError{" + "code='" + code + '\'' + ", message='" + getMessage()
                        + '}';
    }
}
