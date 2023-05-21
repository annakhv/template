package com.messenger.exceptions;

public class RequiredFiledsMissingException extends RuntimeException {
    public RequiredFiledsMissingException(String message) {
        super(message);
    }
}
