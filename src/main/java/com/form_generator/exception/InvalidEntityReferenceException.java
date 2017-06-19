package com.form_generator.exception;

/**
 * Created by david on 6/18/17.
 */
public class InvalidEntityReferenceException extends RuntimeException {
    public InvalidEntityReferenceException(String message) {
        super(message);
    }
}
