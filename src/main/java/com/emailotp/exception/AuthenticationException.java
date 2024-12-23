package com.emailotp.exception;

public class AuthenticationException extends Exception {
    String message;

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }
}
