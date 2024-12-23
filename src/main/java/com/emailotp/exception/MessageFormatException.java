package com.emailotp.exception;

public class MessageFormatException extends Exception{
    String message;

    public MessageFormatException(String message){
        super(message);
        this.message = message;

    }
}
