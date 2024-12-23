package com.emailotp.exception;

public class ValidationException extends RuntimeException {

    String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }
}