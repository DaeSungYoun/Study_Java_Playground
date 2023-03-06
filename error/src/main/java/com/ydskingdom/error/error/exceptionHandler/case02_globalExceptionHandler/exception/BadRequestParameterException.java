package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception;

public class BadRequestParameterException extends RuntimeException {
    public BadRequestParameterException(String message) {
        super(message);
    }
}
