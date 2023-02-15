package com.harera.hayat.common.exception;

import com.harera.hayat.common.model.BaseDocument;

import lombok.Getter;

@Getter
public class DocumentNotFoundException extends RuntimeException {

    private String code;

    public DocumentNotFoundException(String message) {
        super(message);
        this.code = null;
    }

    public DocumentNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    public DocumentNotFoundException(Class<? extends BaseDocument> clazz, long id) {
        super(String.format("[%s] with id [%s] is not found", clazz.getSimpleName(), id));
        this.code = null;
    }

    public DocumentNotFoundException(Class<? extends BaseDocument> clazz, long id,
                    String code) {
        this(String.format(clazz.getSimpleName(), id));
        this.code = code;
    }
}
