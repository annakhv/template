package com.messenger.exceptions;

public class ModeNotAvailableException extends RuntimeException{
    public ModeNotAvailableException(String message){
        super(message);
    }
}
