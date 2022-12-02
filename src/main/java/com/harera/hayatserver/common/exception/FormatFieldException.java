package com.harera.hayatserver.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormatFieldException extends FieldException {

    public FormatFieldException(String code, String field, String value) {
        super(String.format("Invalid %s: %s Format", field, value), code, field);
    }
}
