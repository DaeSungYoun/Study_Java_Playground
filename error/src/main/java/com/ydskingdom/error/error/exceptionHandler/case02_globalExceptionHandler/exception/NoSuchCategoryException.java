package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception;

public class NoSuchCategoryException extends RuntimeException {

    private static final String MESSAGE = "카테고리 정보가 없습니다.";

    public NoSuchCategoryException() {
        super(MESSAGE);
    }
}
