package com.emailotp.exception;

public class MailException extends RuntimeException {

    String message;

    public MailException(String message) {
        super(message);
        this.message = message;
    }
}