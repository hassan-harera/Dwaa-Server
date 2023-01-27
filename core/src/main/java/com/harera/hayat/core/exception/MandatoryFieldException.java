package com.harera.hayat.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MandatoryFieldException
                extends com.harera.hayat.core.exception.FieldException {

    public MandatoryFieldException(String code, String field) {
        super(String.format("Field [%s] is mandatory", field), code, field);
    }
}
