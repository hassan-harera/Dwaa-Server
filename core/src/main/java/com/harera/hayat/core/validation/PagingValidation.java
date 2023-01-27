package com.harera.hayat.core.validation;

public class PagingValidation {

    public static void validate(Integer page, Integer size) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number cannot be less " +
                    "than one.");
        }

        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        if (size > 25) {
            throw new IllegalArgumentException("Page size must not be greater" +
                    " than 25!");
        }
    }
}
