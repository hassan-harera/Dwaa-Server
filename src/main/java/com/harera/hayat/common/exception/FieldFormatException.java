package com.harera.hayat.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldFormatException extends FieldException {

    public FieldFormatException(String code, String field, String pattern) {
        super(String.format("Incorrect format for field %s: must follow %s", field,
                        pattern), code, field);
    }
}
